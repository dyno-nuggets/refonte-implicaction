import {Component, OnInit} from '@angular/core';
import {BehaviorSubject, Observable, of, zip} from 'rxjs';
import {CategoryService, ITree} from '../../../../forum/services/category.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Category} from '../../../../forum/model/category';
import {SidebarContentComponent} from '../../../../shared/models/sidebar-props';
import {CategoryTreeSelectNode} from '../../../../forum/model/categoryTreeSelectNode';
import {ToasterService} from '../../../../core/services/toaster.service';
import {EditCategoryPayload} from '../../../../forum/model/editCategoryPayload';

export type EditCategoryFormProps = {
  category: Category;
};

export type FormGroupContent = {
  title: FormControl<string>,
  description: FormControl<string>,
  category: FormControl<CategoryTreeSelectNode>
};


@Component({
  selector: 'app-edit-category-form',
  templateUrl: './edit-category-form.component.html',
  styleUrls: ['./edit-category-form.component.scss']
})
export class EditCategoryFormComponent extends SidebarContentComponent<EditCategoryFormProps> implements OnInit {


  categoriesNodes$: Observable<ITree>;
  currentCategory: Observable<Category>;
  categoryForm$: Observable<unknown>;
  categoryForm = new BehaviorSubject<FormGroup<FormGroupContent>>(null);

  constructor(private categoryService: CategoryService, private toastService: ToasterService) {
    super();
  }

  ngOnInit(): void {
    this.currentCategory = of(this.sidebarInput.category);
    this.categoriesNodes$ = this.categoryService.getCategoriesTreeSelectNode({
      selectable: () => true
    });
    this.categoriesNodes$.subscribe(res => {
      console.log(res);
    });

    zip(this.currentCategory, this.categoriesNodes$).subscribe(([category, {map}]) =>
      this.categoryForm.next(this.editCategoryForm(category, map.get(category.parentId)))
    );
    this.categoryForm$ = this.categoryForm.asObservable();
  }

  editCategoryForm(category: Category, categoryNode: CategoryTreeSelectNode | null): FormGroup<FormGroupContent> {
    return new FormGroup({
      title: new FormControl<string>(category.title, Validators.required),
      description: new FormControl<string>(category.description, Validators.required),
      category: new FormControl(categoryNode)
    });
  }

  onSubmit(): void {
    const categoryForm = this.categoryForm.getValue();
    const editCategory: EditCategoryPayload = {
      id: this.sidebarInput.category.id,
      title: categoryForm.value.title,
      description: categoryForm.value.description,
      parentId: categoryForm.value.category?.id
    };

    this.categoryService.editCategory(editCategory).subscribe(res => {
      this.toastService.success('Catégorie modifiée!', 'La catégorie a bien été modifiée');
    }, error => {
      console.log(error);
      this.toastService.error('Oops', error.error.errorMessage);
    });

  }

}
