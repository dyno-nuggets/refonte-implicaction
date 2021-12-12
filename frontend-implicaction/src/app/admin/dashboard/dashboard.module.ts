import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './dashboard.component';
import {AdminOverviewComponent} from './components/admin-overview/admin-overview.component';
import {DashboardRoutingModule} from './dashboard-routing.module';
import {UsersModule} from '../users/users.module';
import {AdminJobsModule} from '../jobs/admin-jobs.module';
import {AdminModule} from '../admin.module';


@NgModule({
  declarations: [
    DashboardComponent,
    AdminOverviewComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    UsersModule,
    AdminJobsModule,
    AdminModule,
  ]
})
export class DashboardModule {
}
