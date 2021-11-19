import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {JobDetailsComponent} from './components/job-details/job-details.component';
import {JobsListComponent} from './components/jobs-list/jobs-list.component';
import {AuthGuard} from '../core/guards/auth.guard.service';
import {RoleEnumCode} from '../shared/enums/role.enum';

const routes: Routes = [
  {
    path: ':jobId',
    component: JobDetailsComponent,
    canActivate: [AuthGuard],
    data: {
      allowedRoles: [RoleEnumCode.PREMIUM, RoleEnumCode.ADMIN]
    }
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
