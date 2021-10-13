import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {JobsTableComponent} from './components/jobs-table/jobs-table.component';
import {JobsComponent} from './jobs.component';
import {JobsRoutingModule} from './jobs-routing.module';
import {TableModule} from 'primeng/table';


@NgModule({
  declarations: [
    JobsComponent,
    JobsTableComponent,
  ],
  imports: [
    CommonModule,
    JobsRoutingModule,
    TableModule,
  ]
})
export class JobsModule {
}
