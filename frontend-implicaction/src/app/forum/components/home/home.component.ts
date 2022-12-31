import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {CategoryService} from "../../services/category.service";
import {Category} from "../../model/category";
import {map, switchMap} from "rxjs/operators";

export type CategoryWithChildren = Omit<Category, "children"> & { children: Category[] };

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  categories$: Observable<CategoryWithChildren[]>;

  constructor(private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.categories$ = this.categoryService.getRootCategories()
      .pipe(switchMap(parentCategories => {
        const children$ = this.fetchRootCategoriesChildren(parentCategories);

        return children$.pipe(
          map(children => this.childrenToMap(children)),
          map(childrenMap => this.categoriesToCategoriesWithChildren(parentCategories, childrenMap))
        );
      }));
  }

  private fetchRootCategoriesChildren(categories: Category[]) {
    const categoriesToFetch = categories.flatMap(category => category.children);
    return this.categoryService.getCategories(categoriesToFetch);
  }

  private childrenToMap(children: Category[]) {
    const categoryMap = new Map<number, Category>();
    children.forEach(child => categoryMap.set(child.id, child));
    return categoryMap;
  }

  private categoriesToCategoriesWithChildren(parents: Category[], childrenMap: Map<number, Category>) {
    return parents.map(parentCategory => ({
      ...parentCategory,
      children: parentCategory.children.map(childId => childrenMap.get(childId))
    }));
  }

}
