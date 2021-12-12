import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DiscussionComponent} from './discussion.component';
import {PostListComponent} from './components/post-list/post-list.component';
import {PostDetailComponent} from './components/post-detail/post-detail.component';
import {GroupListComponent} from './components/group-list/group-list.component';

const routes: Routes = [
  {
    path: '',
    component: DiscussionComponent,
    children: [
      {
        path: '',
        component: PostListComponent,
        outlet: 'discussion-content'
      }
    ]
  },
  {
    path: 'groups',
    component: DiscussionComponent,
    children: [
      {
        path: '',
        component: GroupListComponent,
        outlet: 'discussion-content'
      }
    ]
  },
  {
    path: ':postId',
    component: DiscussionComponent,
    children: [
      {
        path: '',
        component: PostDetailComponent,
        outlet: 'discussion-content'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class DiscussionRoutingModule {
}
