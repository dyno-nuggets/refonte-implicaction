import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ForumComponent} from './forum.component';
import {HomeComponent} from "./components/home/home.component";
import {CategoryContentComponent} from "./components/category-content/category-content.component";

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

    path: 'category/:id',
    component: ForumComponent,
    children: [
      {
        path: '',
        component: CategoryContentComponent,
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
