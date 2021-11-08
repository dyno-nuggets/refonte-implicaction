import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DiscussionComponent} from './discussion.component';
import {PostDetailComponent} from './components/post-detail/post-detail.component';

const routes: Routes = [
  {
    path: '',
    component: DiscussionComponent
  },
  {
    path: ':postId',
    component: PostDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class DiscussionRoutingModule {
}
