import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {JobsListComponent} from './components/jobs-list/jobs-list.component';
import {JobCardComponent} from './components/job-card/job-card.component';
import {PaginatorModule} from 'primeng/paginator';
import {SharedModule} from '../shared/shared.module';
import {JobDetailsComponent} from './components/job-details/job-details.component';
import {JobRoutingModule} from './job-routing.module';
import {FeatherModule} from 'angular-feather';
import {CardModule} from 'primeng/card';
import {DropdownModule} from 'primeng/dropdown';


@NgModule({
  declarations: [
    JobsListComponent,
    JobDetailsComponent,
    JobCardComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    JobRoutingModule,
    FeatherModule,
    PaginatorModule,
    CardModule,
    DropdownModule
  ]
})
export class JobModule {
}
