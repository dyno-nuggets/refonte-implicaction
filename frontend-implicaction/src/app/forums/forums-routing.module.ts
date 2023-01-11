import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ForumsComponent} from './forums.component';
import {PostDetailComponent} from './components/post-detail/post-detail.component';
import {GroupListComponent} from './components/group-list/group-list.component';
import {ForumPostsComponent} from "./components/forum-posts/forum-posts.component";

const routes: Routes = [
  {
    path: '',
    component: ForumsComponent,
  },
  {
    path: 'groups',
    component: GroupListComponent, // type forum
  },
  {
    path: ':forumId',
    component: ForumPostsComponent, // type forum
  },
  {
    path: ':forumId/:postId',
    component: PostDetailComponent, // type forum
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ForumsRoutingModule {
}
