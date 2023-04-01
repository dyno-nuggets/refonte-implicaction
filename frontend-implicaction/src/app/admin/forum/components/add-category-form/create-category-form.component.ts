import {Component, OnInit} from '@angular/core';
import {SidebarContentComponent} from '../../../../shared/models/sidebar-props';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Category} from '../../../../forum/model/category';
import {Observable} from 'rxjs';
import {CategoryService, ITree} from '../../../../forum/services/category.service';
import {ToasterService} from '../../../../core/services/toaster.service';
import {CategoryPayload} from '../../../../forum/model/categoryPayload';

@Component({
  selector: 'app-add-category-form',
  templateUrl: './create-category-form.component.html',
  styleUrls: ['./create-category-form.component.scss']
})
export class CreateCategoryFormComponent extends SidebarContentComponent<never> implements OnInit {

  categoriesNodes$: Observable<ITree>;

  categoryForm = new FormGroup({
    title: new FormControl<string>('', Validators.required),
    description: new FormControl<string>('', Validators.required),
    category: new FormControl<Category>(null)
  });

  constructor(private sidebarService: SidebarService,
              private categoryService: CategoryService,
              private toastService: ToasterService
  ) {
    super();
  }

  ngOnInit(): void {
    this.categoriesNodes$ = this.categoryService.getCategoriesTreeSelectNode({
      selectable: () => true
    });
  }

  onSubmit(): void {

    const createCategory: CategoryPayload = {
      title: this.categoryForm.value.title,
      description: this.categoryForm.value.description,
      parentId: this.categoryForm.value.category !== null ? this.categoryForm.value.category.id : null
    };


    this.categoryService.createCategory(createCategory).subscribe(res => {
      this.toastService.success('Categorie créé!', 'La categorie a bien été créé');
    }, error => {
      this.toastService.error('Oops', 'Une erreur est survenue');
    }, () => {
      this.sidebarService.close();
    });
  }

}
