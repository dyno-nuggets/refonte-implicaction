import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';
import {PaginatorModule} from 'primeng/paginator';
import {AvatarModule} from 'primeng/avatar';
import {SkeletonModule} from 'primeng/skeleton';
import {EditorModule} from 'primeng/editor';
import {TableModule} from 'primeng/table';

import {ForumComponent} from './forum.component';
import {ForumRoutingModule} from './forum-routing.module';
import {SharedModule} from '../shared/shared.module';
import {IconsModule} from '../icons/icons.module';
import {HomeComponent} from './components/home/home.component';
import {CategoryContentComponent} from './components/category-content/category-content.component';
import {SubCategoryListComponent} from './components/sub-category-list/sub-category-list.component';
import {TopicListComponent} from './components/topic-list/topic-list.component';
import {TopicContentComponent} from './components/topic-content/topic-content.component';
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {RippleModule} from "primeng/ripple";
import {InputTextareaModule} from "primeng/inputtextarea";
import {CheckboxModule} from "primeng/checkbox";
import {InputTextModule} from "primeng/inputtext";
import {TreeSelectModule} from "primeng/treeselect";
import {CreateTopicFormComponent} from './components/create-topic-form/create-topic-form.component';
import {CreateTopicButtonComponent} from './components/create-topic-button/create-topic-button.component';
import {ForumBlockComponent} from './components/forum-block/forum-block.component';
import {EditTopicFormComponent} from './components/edit-topic-form/edit-topic-form.component';
import {EditTopicButtonComponent} from './components/edit-topic-button/edit-topic-button.component';


@NgModule({
  declarations: [
    ForumComponent,
    HomeComponent,
    CategoryContentComponent,
    SubCategoryListComponent,
    TopicListComponent,
    TopicContentComponent,
    CreateTopicFormComponent,
    CreateTopicButtonComponent,
    ForumBlockComponent,
    EditTopicFormComponent,
    EditTopicButtonComponent,
  ],
  imports: [
    CommonModule,
    ForumRoutingModule,
    SharedModule,
    PaginatorModule,
    AvatarModule,
    SkeletonModule,
    ReactiveFormsModule,
    EditorModule,
    IconsModule,
    TableModule,
    ButtonModule,
    DialogModule,
    RippleModule,
    InputTextareaModule,
    CheckboxModule,
    InputTextModule,
    TreeSelectModule,
  ]
})
export class ForumModule {
}
