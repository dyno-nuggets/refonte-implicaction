import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminRoutingModule} from './admin-routing.module';
import {CompaniesComponent} from './companies/companies.component';
import {TableModule} from 'primeng/table';
import {CompaniesModule} from './companies/companies.module';
import {FeatherModule} from 'angular-feather';
import {PendingGroupTableComponent} from './groups/pending-group-table/pending-group-table.component';
import {AdminPageComponent} from './pages/admin-page/admin-page.component';
import {SharedModule} from "../shared/shared.module";
import {UsersTableComponent} from './components/users/users-table/users-table.component';
import {UserStatusBadgeComponent} from './components/users/user-status-badge/user-status-badge.component';
import {MultiSelectModule} from "primeng/multiselect";
import {FormsModule} from "@angular/forms";
import {TooltipModule} from "primeng/tooltip";
import {DropdownModule} from "primeng/dropdown";
import {UserManagementWidgetComponent} from './widgets/user-management-wiget/user-management-widget.component';
import {UserFragmentComponent} from './fragments/user-fragment/user-fragment.component';
import {SkeletonModule} from "primeng/skeleton";
import {DashboardFragmentComponent} from "./fragments/dashboard-fragment/dashboard-fragment.component";
import {UserCounterWidgetComponent} from './widgets/user-counter-widget/user-counter-widget.component';
import {CounterCardComponent} from './components/counter-card/counter-card.component';
import {PendingJobTableComponent} from "./components/users/pending-job-table/pending-job-table.component";
import { JobCounterWidgetComponent } from './widgets/job-counter-widget/job-counter-widget.component';


@NgModule({
  declarations: [
    CompaniesComponent,
    PendingGroupTableComponent,
    AdminPageComponent,
    UsersTableComponent,
    UserStatusBadgeComponent,
    UserManagementWidgetComponent,
    UserFragmentComponent,
    DashboardFragmentComponent,
    UserCounterWidgetComponent,
    CounterCardComponent,
    PendingJobTableComponent,
    JobCounterWidgetComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule,
    TableModule,
    CompaniesModule,
    FeatherModule,
    SharedModule,
    MultiSelectModule,
    FormsModule,
    TooltipModule,
    DropdownModule,
    SkeletonModule
  ],
  exports: [
    PendingGroupTableComponent,
    UsersTableComponent,
    UserManagementWidgetComponent
  ]
})
export class AdminModule {
}
