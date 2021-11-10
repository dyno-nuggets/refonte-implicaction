import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminComponent} from './admin.component';
import {JobFilterComponent} from '../shared/components/job-filter/job-filter.component';
import {CompanyFilterComponent} from '../shared/components/company-filter/company-filter.component';


const routes: Routes = [
  {
    path: '', redirectTo: 'dashboard', pathMatch: 'full'
  },
  {
    path: 'dashboard', component: AdminComponent, children: [
      {
        path: '',
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
        outlet: 'admin-content'
      }
    ]
  },
  {
    path: 'users', component: AdminComponent, children: [
      {
        path: '',
        loadChildren: () => import('./users/users.module').then(m => m.UsersModule),
        outlet: 'admin-content'
      }
    ]
  },
  {
    path: 'jobs', component: AdminComponent, children: [
      {
        path: '',
        loadChildren: () => import('./jobs/admin-jobs.module').then(m => m.AdminJobsModule),
        outlet: 'admin-content',

      },
      {
        path: '',
        component: JobFilterComponent,
        outlet: 'admin-filter'
      }
    ]
  },
  {
    path: 'companies', component: AdminComponent, children: [
      {
        path: '',
        loadChildren: () => import('./companies/companies.module').then(m => m.CompaniesModule),
        outlet: 'admin-content'
      },
      {
        path: '',
        component: CompanyFilterComponent,
        outlet: 'admin-filter'
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
