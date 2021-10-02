import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {JobsListComponent} from './components/jobs-list/jobs-list.component';
import {JobCardComponent} from './components/job-card/job-card.component';
import {SharedModule} from '../shared/shared.module';
import {JobRoutingModule} from './job-routing.module';
import {FeatherModule} from 'angular-feather';


@NgModule({
  declarations: [
    JobsListComponent,
    JobCardComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    JobRoutingModule,
    FeatherModule
  ]
})
export class JobModule {
}
