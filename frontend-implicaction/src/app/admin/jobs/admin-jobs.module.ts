import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {JobsTableComponent} from './components/jobs-table/jobs-table.component';
import {AdminJobsComponent} from './admin-jobs.component';
import {AdminJobsRoutingModule} from './admin-jobs-routing.module';
import {TableModule} from 'primeng/table';


@NgModule({
  declarations: [
    AdminJobsComponent,
    JobsTableComponent,
  ],
  imports: [
    CommonModule,
    AdminJobsRoutingModule,
    TableModule,
  ]
})
export class AdminJobsModule {
}
