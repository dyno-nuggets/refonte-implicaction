import {Component, OnInit} from '@angular/core';
import {Category} from '../../../../forum/model/category';
import {CategoryService} from '../../../../forum/services/category.service';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {CreateCategoryFormComponent} from '../add-category-form/create-category-form.component';
import {ToasterService} from '../../../../core/services/toaster.service';
import {EditCategoryFormComponent} from '../edit-category-form/edit-category-form.component';
import {CategoryWithParent} from '../../../../forum/model/categoryWithParent';

interface TableCategory {
  id: number;
  title: string;
  description: string;
  parent: Category;
}

@Component({
  selector: 'app-category-row',
  templateUrl: './category-row.component.html',
  styleUrls: ['./category-row.component.scss']
})
export class CategoryRowComponent implements OnInit {

  categories: TableCategory[];

  constructor(private categoryService: CategoryService,
              private sidebarService: SidebarService,
              private toasterService: ToasterService) {
  }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(result => {
      const categoryMap = new Map<number, Category>(this.categoriesToArray(result));
      this.categories = result.map(value => {
        const tableCategory: TableCategory = {
          id: value.id,
          title: value.title,
          description: value.description,
          parent: categoryMap.get(value.parentId)
        };
        return tableCategory;
      });
    });
  }

  categoriesToArray(categories: Category[]): [number, Category][] {
    return categories.map(value => [value.id, value] as [number, Category]);
  }

  onClickEdit(category: CategoryWithParent): void {
    this.sidebarService.open({
      title: 'Editer une catégorie',
      component: EditCategoryFormComponent,
      width: 500,
      input: {
        category: {
          ...category,
          parentId: category.parent?.id,
          children: []
        }
      }
    });
  }

  onClickDelete(category): void {
    this.categoryService.deleteCategory(category.id).subscribe(() => {
      this.toasterService.success('Categorie', 'Catégorie supprimée avec succès !');
    }, error => {
      this.toasterService.error('Erreur', error.error.errorMessage);
    });
  }

  onClickAdd(): void {
    this.sidebarService.open({
      title: 'Creer une categorie',
      component: CreateCategoryFormComponent,
      width: 500
    });
  }
}
