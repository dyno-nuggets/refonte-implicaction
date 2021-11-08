import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PostListComponent} from './components/post-list/post-list.component';
import {BlogComponent} from './blog.component';
import {BlogRoutingModule} from './blog-routing.module';
import {SharedModule} from '../shared/shared.module';
import {PaginatorModule} from 'primeng/paginator';
import {VoteboxComponent} from './components/votebox/votebox.component';
import {AvatarModule} from 'primeng/avatar';
import {PostDetailsComponent} from './components/post-details/post-details.component';


@NgModule({
  declarations: [
    PostListComponent,
    PostDetailsComponent,
    BlogComponent,
    VoteboxComponent,
  ],
  imports: [
    CommonModule,
    BlogRoutingModule,
    SharedModule,
    PaginatorModule,
    AvatarModule
  ]
})
export class BlogModule {
}
