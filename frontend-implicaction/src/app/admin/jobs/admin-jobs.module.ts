import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminJobsComponent} from './admin-jobs.component';
import {AdminJobsRoutingModule} from './admin-jobs-routing.module';
import {TableModule} from 'primeng/table';
import {EditorModule} from 'primeng/editor';
import {ReactiveFormsModule} from '@angular/forms';
import {FeatherModule} from 'angular-feather';
import {DropdownModule} from 'primeng/dropdown';
import {CheckboxModule} from 'primeng/checkbox';
import {PendingJobTableComponent} from './components/pending-job-table/pending-job-table.component';
import {JobPostingFormComponent} from './components/job-posting-form/job-posting-form.component';


@NgModule({
  declarations: [
    AdminJobsComponent,
    JobPostingFormComponent,
    PendingJobTableComponent
  ],
  exports: [
    PendingJobTableComponent
  ],
  imports: [
    CommonModule,
    AdminJobsRoutingModule,
    TableModule,
    EditorModule,
    ReactiveFormsModule,
    FeatherModule,
    DropdownModule,
    CheckboxModule
  ]
})
export class AdminJobsModule {
}
