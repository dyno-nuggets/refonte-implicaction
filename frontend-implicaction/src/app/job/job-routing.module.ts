import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {JobDetailsComponent} from './components/job-details/job-details.component';
import {JobsListComponent} from './components/jobs-list/jobs-list.component';

const routes: Routes = [
  {
    path: ':jobId',
    component: JobDetailsComponent,
  },
  {
    path: '',
    component: JobsListComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class JobRoutingModule {
}
