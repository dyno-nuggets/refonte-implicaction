import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {JobFilterComponent} from '../shared/components/job-filter/job-filter.component';
import {CompanyFilterComponent} from '../shared/components/company-filter/company-filter.component';
import {AdminPageComponent} from "./pages/admin-page/admin-page.component";
import {UserFragmentComponent} from "./fragments/user-fragment/user-fragment.component";
import {DashboardFragmentComponent} from "./fragments/dashboard-fragment/dashboard-fragment.component";


const routes: Routes = [
  {
    path: '', redirectTo: 'dashboard', pathMatch: 'full'
  },
  {
    path: 'dashboard', component: AdminPageComponent, children: [
      {
        path: '',
        component: DashboardFragmentComponent,
        outlet: 'fragment-content'
      }
    ]
  },
  {
    path: 'users', component: AdminPageComponent, children: [
      {
        path: '',
        component: UserFragmentComponent,
        outlet: 'fragment-content'
      }
    ]
  },
  {
    path: 'jobs', component: AdminPageComponent, children: [
      {
        path: '',
        loadChildren: () => import('./jobs/admin-jobs.module').then(m => m.AdminJobsModule),
        outlet: 'fragment-content',

      },
      {
        path: '',
        component: JobFilterComponent,
        outlet: 'fragment-content'
      }
    ]
  },
  {
    path: 'companies', component: AdminPageComponent, children: [
      {
        path: '',
        loadChildren: () => import('./companies/companies.module').then(m => m.CompaniesModule),
        outlet: 'fragment-content'
      },
      {
        path: '',
        component: CompanyFilterComponent,
        outlet: 'fragment-content'
      }
    ]
  },
  {
    path: 'forum', component: AdminPageComponent, children: [
      {
        path: '',
        loadChildren: () => import('./forum/forum.module').then(m => m.ForumModule),
        outlet: 'fragment-content'
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
