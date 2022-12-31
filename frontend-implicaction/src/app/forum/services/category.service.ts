import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiEndpointsService} from "../../core/services/api-endpoints.service";
import {Observable} from "rxjs";
import {Category} from "../model/category";
import {Pageable} from "../../shared/models/pageable";
import {Topic} from "../model/topic";
import {map} from "rxjs/operators";

export type CategoryNode = Category & { children: CategoryNode[]; };

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(
    private http: HttpClient,
    private apiEndpointService: ApiEndpointsService
  ) {
  }

  getCategories(): Observable<Category[]>;
  getCategories(ids: number[]): Observable<Category[]>;
  getCategories(ids?: number[]): Observable<Category[]> {
    if (ids) {
      return this.http.get<Category[]>(this.apiEndpointService.getCategories(ids));
    }
    return this.http.get<Category[]>(this.apiEndpointService.getCategories());
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

  getCategory(id: number): Observable<Category> {
    return this.http
      .get<Category[]>(this.apiEndpointService.getCategory(id))
      .pipe(map(categories => categories[0]));
  }

  getCategoryTopics(id: number, pageable: Pageable<any>): Observable<Pageable<Topic>> {
    return this.http.get<Pageable<Topic>>(this.apiEndpointService.getCategoryTopics(id, pageable));
  }
}
