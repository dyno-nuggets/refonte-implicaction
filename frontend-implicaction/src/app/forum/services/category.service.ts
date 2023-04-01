import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {ApiEndpointsService} from "../../core/services/api-endpoints.service";
import {Category} from "../model/category";
import {Topic} from "../model/topic";
import {Pageable} from "../../shared/models/pageable";
import {CategoryTreeSelectNode} from '../model/categoryTreeSelectNode';
import {CategoryPayload} from '../model/categoryPayload';

export interface ITree {
  tree: CategoryTreeSelectNode[];
  map: Map<number, CategoryTreeSelectNode>;
}

export interface GetCategoriesTreeSelectNodesOption {
  selectable?: (categoryNode: CategoryNode) => boolean;
  filter?: (categoryNode: CategoryNode) => boolean;
  notHaveParent?: (categoryNode: CategoryNode) => boolean;
}

export type CategoryNode = Category & { children: CategoryNode[]; };
export type GetCategoriesOptions = { withRecentlyUpdatedTopic: boolean };

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(
    private http: HttpClient,
    private apiEndpointService: ApiEndpointsService
  ) {
  }

  getCategories(opts?: GetCategoriesOptions): Observable<Category[]>;
  getCategories(ids: number[], opts?: GetCategoriesOptions): Observable<Category[]>;
  getCategories(ids?: number[] | GetCategoriesOptions, opts?: GetCategoriesOptions): Observable<Category[]> {
    if (ids instanceof Array) {
      return this.http.get<Category[]>(this.apiEndpointService.getCategories(ids, opts));
    }
    return this.http.get<Category[]>(this.apiEndpointService.getCategories(opts));
  }

  getRootCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.apiEndpointService.getRootCategories());
  }

  getCategoryTree(): Observable<CategoryNode[]> {
    return this.getCategories().pipe(map(categories => {
      // Store each category node by its id
      const categoryNodeMap = new Map<number, CategoryNode>();

      // convert each category into a category node & add it to the map
      const categoryNodes = categories.map(category => {
        const newCategoryNode = {...category, children: []};
        categoryNodeMap.set(category.id, newCategoryNode);
        return newCategoryNode;
      });

      // add each categoryNode to its parent
      categoryNodes.forEach(categoryNode => {
        const parentCategory = categoryNodeMap.get(categoryNode.parentId);
        if (!parentCategory) {
          return;
        }

        parentCategory.children.push(categoryNode);
      });

      // only keep the root categories
      return categoryNodes.filter(categoryNode => categoryNode.parentId === null);
    }));
  }

  getCategoriesTreeSelectNode(options?: GetCategoriesTreeSelectNodesOption): Observable<ITree> {
    const nodeMap: Map<number, CategoryTreeSelectNode> = new Map();

    const categoryToCategoryTreeSelectNode = (categories: CategoryNode[]) => {
      return categories
        .filter(options?.filter ?? (() => true))
        .map((categoryNode) => {
          const {id, title, children} = categoryNode;
          const node: CategoryTreeSelectNode = {
            id,
            label: title,
            selectable: options?.selectable(categoryNode) ?? true,
            data: '',
            children: categoryToCategoryTreeSelectNode(children)
          };
          nodeMap.set(id, node);
          return node;
        });
    };
    return this.getCategoryTree().pipe(
      map(categoryToCategoryTreeSelectNode),
      map((tree) => ({tree, map: nodeMap}))
    );
  }

  getCategory(id: number): Observable<Category> {
    return this.http
      .get<Category[]>(this.apiEndpointService.getCategory(id))
      .pipe(map(categories => categories[0]));
  }

  getCategoryTopics(id: number, pageable: Pageable<any>): Observable<Pageable<Topic>> {
    return this.http.get<Pageable<Topic>>(this.apiEndpointService.getCategoryTopics(id, pageable));
  }

  createCategory(category: CategoryPayload): Observable<Category> {
    return this.http.post<Category>(this.apiEndpointService.createCategory(), category);
  }

  deleteCategory(categoryId: number) {
    return this.http.delete(this.apiEndpointService.deleteCategory(categoryId));
  }

  editCategory(category: CategoryPayload): Observable<Category> {
    return this.http.put<Category>(this.apiEndpointService.editCategory(), category);
  }

}
