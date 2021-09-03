import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserComponent} from "./user.component";


const routes: Routes = [
  {
    path: 'profile', component: UserComponent, children: [
      {
        path: '',
        loadChildren: () => import('./profile/profile.module').then(m => m.ProfileModule),
        outlet: 'profile-content'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {
}
