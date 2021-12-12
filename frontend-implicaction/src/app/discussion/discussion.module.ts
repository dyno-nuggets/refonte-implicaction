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
import {OptionMenuComponent} from './components/option-menu/option-menu.component';
import {CreateGroupFormComponent} from './components/create-group-form/create-group-form.component';
import {ReactiveFormsModule} from '@angular/forms';
import {TopGroupListingComponent} from './components/top-group-listing/top-group-listing.component';
import {TopGroupSkeletonComponent} from './components/top-group-skeleton/top-group-skeleton.component';
import {CommentListComponent} from './components/comment-list/comment-list.component';
import {EditorModule} from 'primeng/editor';
import {CommentTileComponent} from './components/comment-tile/comment-tile.component';
import {CommentSkeletonComponent} from './components/comment-skeleton/comment-skeleton.component';
import {CreatePostFormComponent} from './components/create-post-form/create-post-form.component';
import {IconsModule} from '../icons/icons.module';
import {GroupListComponent} from './components/group-list/group-list.component';
import {TableModule} from 'primeng/table';
import { GroupCardComponent } from './components/group-card/group-card.component';


@NgModule({
  declarations: [
    PostListComponent,
    PostTileComponent,
    DiscussionComponent,
    VoteboxComponent,
    PostDetailComponent,
    PostSkeletonComponent,
    OptionMenuComponent,
    CreateGroupFormComponent,
    TopGroupListingComponent,
    TopGroupSkeletonComponent,
    CreatePostFormComponent,
    CommentListComponent,
    CommentTileComponent,
    CommentSkeletonComponent,
    GroupListComponent,
    GroupCardComponent,
  ],
  imports: [
    CommonModule,
    DiscussionRoutingModule,
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
export class DiscussionModule {
}
