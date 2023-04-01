import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ForumComponent} from './forum.component';
import {ForumRoutingModule} from './forum-routing.module';
import {TableModule} from 'primeng/table';
import {ButtonModule} from 'primeng/button';
import {RippleModule} from 'primeng/ripple';
import {CategoryRowComponent} from './components/category-row/category-row.component';
import {CreateCategoryFormComponent} from './components/add-category-form/create-category-form.component';
import {EditorModule} from 'primeng/editor';
import {TreeSelectModule} from 'primeng/treeselect';
import {ReactiveFormsModule} from '@angular/forms';
import { EditCategoryFormComponent } from './components/edit-category-form/edit-category-form.component';


@NgModule({
  declarations: [
    ForumComponent,
    CategoryRowComponent,
    CreateCategoryFormComponent,
    EditCategoryFormComponent
  ],
  imports: [
    ForumRoutingModule,
    CommonModule,
    TableModule,
    ButtonModule,
    RippleModule,
    EditorModule,
    TreeSelectModule,
    ReactiveFormsModule
  ]
})
export class ForumModule {
}
