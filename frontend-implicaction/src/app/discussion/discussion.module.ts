import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PostListComponent} from './components/post-list/post-list.component';
import {DiscussionComponent} from './discussion.component';
import {DiscussionRoutingModule} from './discussion-routing.module';
import {SharedModule} from '../shared/shared.module';
import {PaginatorModule} from 'primeng/paginator';
import {VoteboxComponent} from './components/votebox/votebox.component';
import {AvatarModule} from 'primeng/avatar';
import {PostTileComponent} from './components/post-tile/post-tile.component';
import {PostDetailComponent} from './components/post-detail/post-detail.component';
import {PostSkeletonComponent} from './components/post-skeleton/post-skeleton.component';
import {SkeletonModule} from 'primeng/skeleton';


@NgModule({
  declarations: [
    PostListComponent,
    PostTileComponent,
    DiscussionComponent,
    VoteboxComponent,
    PostDetailComponent,
    PostSkeletonComponent,
  ],
  imports: [
    CommonModule,
    DiscussionRoutingModule,
    SharedModule,
    PaginatorModule,
    AvatarModule,
    SkeletonModule
  ]
})
export class DiscussionModule {
}
