import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './dashboard.component';
import {AdminOverviewComponent} from './components/admin-overview/admin-overview.component';
import {DashboardRoutingModule} from './dashboard-routing.module';


@NgModule({
  declarations: [
    DashboardComponent,
    AdminOverviewComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule {
}
