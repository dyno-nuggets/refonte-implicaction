import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ForumsComponent } from './forums.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';
import { GroupListComponent } from './components/group-list/group-list.component';

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
    path: ':postId',
    component: PostDetailComponent, // type forum
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ForumsRoutingModule {}
