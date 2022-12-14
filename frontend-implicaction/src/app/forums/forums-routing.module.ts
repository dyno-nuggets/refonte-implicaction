import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ForumsComponent} from './forums.component';
import {PostListComponent} from './components/post-list/post-list.component';
import {PostDetailComponent} from './components/post-detail/post-detail.component';
import {GroupListComponent} from './components/group-list/group-list.component';

const routes: Routes = [
  {
    path: '',
    component: ForumsComponent,
    children: [
      {
        path: '',
        component: PostListComponent,
        outlet: 'forums-content'
      }
    ]
  },
  {
    path: 'groups',
    component: ForumsComponent,
    children: [
      {
        path: '',
        component: GroupListComponent,
        outlet: 'forums-content'
      }
    ]
  },
  {
    path: ':postId',
    component: ForumsComponent,
    children: [
      {
        path: '',
        component: PostDetailComponent,
        outlet: 'forums-content'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class ForumsRoutingModule {
}
