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


@NgModule({
  declarations: [
    PostListComponent,
    PostTileComponent,
    DiscussionComponent,
    VoteboxComponent,
    PostDetailComponent,
  ],
  imports: [
    CommonModule,
    DiscussionRoutingModule,
    SharedModule,
    PaginatorModule,
    AvatarModule
  ]
})
export class DiscussionModule {
}
