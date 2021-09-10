import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminComponent} from './admin.component';


const routes: Routes = [
  {
    path: '', redirectTo: 'dashboard', pathMatch: 'full'
  },
  {
    path: 'dashboard', component: AdminComponent, children: [
      {
        path: '',
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
        outlet: 'admin-content'
      }
    ]
  },
  {
    path: 'users', component: AdminComponent, children: [
      {
        path: '',
        loadChildren: () => import('./users/users.module').then(m => m.UsersModule),
        outlet: 'admin-content'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}