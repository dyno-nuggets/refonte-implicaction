import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {JobsTableComponent} from './components/jobs-table/jobs-table.component';
import {AdminJobsComponent} from './admin-jobs.component';
import {AdminJobsRoutingModule} from './admin-jobs-routing.module';
import {TableModule} from 'primeng/table';
import {EditorModule} from 'primeng/editor';
import {JobPostingFormComponent} from './components/job-posting-form/job-posting-form.component';
import {ReactiveFormsModule} from '@angular/forms';
import {FeatherModule} from 'angular-feather';


@NgModule({
  declarations: [
    AdminJobsComponent,
    JobsTableComponent,
    JobPostingFormComponent
  ],
  imports: [
    CommonModule,
    AdminJobsRoutingModule,
    TableModule,
    EditorModule,
    ReactiveFormsModule,
    FeatherModule
  ]
})
export class AdminJobsModule {
}
