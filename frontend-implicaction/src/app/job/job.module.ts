import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {JobsListComponent} from './components/jobs-list/jobs-list.component';
import {JobCardComponent} from './components/job-card/job-card.component';
import {SharedModule} from '../shared/shared.module';
import {JobRoutingModule} from './job-routing.module';
import {FeatherModule} from 'angular-feather';
import {PaginatorModule} from 'primeng/paginator';


@NgModule({
  declarations: [
    JobsListComponent,
    JobCardComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    JobRoutingModule,
    FeatherModule,
    PaginatorModule
  ]
})
export class JobModule {
}
