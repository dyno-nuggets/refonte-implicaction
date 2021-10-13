import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {JobsPostingsComponent} from './jobs-postings.component';


const routes: Routes = [
  {path: '', component: JobsPostingsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class JobsPostingsRoutingModule {
}
