import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminJobsComponent} from './admin-jobs.component';
import {AdminJobsRoutingModule} from './admin-jobs-routing.module';
import {TableModule} from 'primeng/table';
import {EditorModule} from 'primeng/editor';
import {JobPostingFormComponent} from './components/job-posting-form/job-posting-form.component';
import {ReactiveFormsModule} from '@angular/forms';
import {FeatherModule} from 'angular-feather';
import {DropdownModule} from 'primeng/dropdown';
import {CheckboxModule} from 'primeng/checkbox';


@NgModule({
  declarations: [
    AdminJobsComponent,
    JobPostingFormComponent
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
