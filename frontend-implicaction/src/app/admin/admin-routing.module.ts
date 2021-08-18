import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminUserComponent} from './components/admin-user/admin-user.component';
import {AdminPageComponent} from './components/admin-page/admin-page.component';
import {AdminDashboardComponent} from './components/admin-dashboard/admin-dashboard.component';


const routes: Routes = [
  {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
  {
    path: 'dashboard', component: AdminPageComponent, children: [
      {path: '', component: AdminDashboardComponent, outlet: 'admin-content'}
    ]
  },
  {
    path: 'pages', component: AdminPageComponent, children: []
  },
  {
    path: 'posts', component: AdminPageComponent, children: []
  },
  {
    path: 'users', component: AdminPageComponent, children: [
      {path: '', component: AdminUserComponent, outlet: 'admin-content'}
    ]
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
