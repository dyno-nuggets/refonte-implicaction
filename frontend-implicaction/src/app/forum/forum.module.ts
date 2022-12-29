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


@NgModule({
  declarations: [
    ForumComponent,
    HomeComponent,
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
    TableModule
  ]
})
export class ForumModule {
}
