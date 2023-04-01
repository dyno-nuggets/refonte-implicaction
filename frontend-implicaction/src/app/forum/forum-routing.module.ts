import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ForumComponent} from './pages/forum.component';
import {HomeComponent} from "./components/home/home.component";
import {CategoryContentComponent} from "./components/category-content/category-content.component";
import {TopicContentComponent} from "./components/topic-content/topic-content.component";

const routes: Routes = [
  {
    path: '',
    component: ForumComponent,
    children: [
      {
        path: '',
        component: HomeComponent,
        outlet: 'forum-content'
      },
    ]
  },
  {
    path: 'categories/:id',
    component: ForumComponent,
    children: [
      {
        path: '',
        component: CategoryContentComponent,
        outlet: 'forum-content'
      }
    ]
  },
  {
    path: 'topics/:id',
    component: ForumComponent,
    children: [
      {
        path: '',
        component: TopicContentComponent,
        outlet: 'forum-content'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ForumRoutingModule {
}
