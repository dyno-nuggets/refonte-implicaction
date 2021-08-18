import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminRoutingModule} from './admin-routing.module';
import {AdminMenuComponent} from './components/admin-menu/admin-menu.component';
import {AdminOverviewComponent} from './components/admin-overview/admin-overview.component';
import {AdminUserComponent} from './components/admin-user/admin-user.component';
import {AdminPageComponent} from './components/admin-page/admin-page.component';
import {AdminDashboardComponent} from './components/admin-dashboard/admin-dashboard.component';


@NgModule({
  declarations: [
    AdminDashboardComponent,
    AdminMenuComponent,
    AdminOverviewComponent,
    AdminPageComponent,
    AdminUserComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule {
}
