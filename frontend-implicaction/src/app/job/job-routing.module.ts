import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {JobDetailsComponent} from './components/job-details/job-details.component';

const routes: Routes = [
  {
    path: ':jobId',
    component: JobDetailsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class JobRoutingModule {
}
