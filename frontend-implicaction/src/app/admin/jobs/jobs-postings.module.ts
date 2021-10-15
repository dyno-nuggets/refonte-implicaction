import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TableModule} from 'primeng/table';
import {TableJobsComponent} from './components/table-jobs/table-jobs.component';
import {JobsPostingsComponent} from './jobs-postings.component';
import {JobPostingFormComponent} from './components/job-posting-form/job-posting-form.component';
import {JobsPostingsRoutingModule} from './jobs-postings-routing.module';
import {CalendarModule} from 'primeng/calendar';
import {ReactiveFormsModule} from '@angular/forms';
import {FeatherModule} from 'angular-feather';
import {EditorModule} from 'primeng/editor';


@NgModule({
  declarations: [
    TableJobsComponent,
    JobsPostingsComponent,
    JobPostingFormComponent
  ],
  imports: [
    CommonModule,
    JobsPostingsRoutingModule,
    TableModule,
    CalendarModule,
    ReactiveFormsModule,
    FeatherModule,
    EditorModule
  ]
})
export class JobsPostingsModule {
}
