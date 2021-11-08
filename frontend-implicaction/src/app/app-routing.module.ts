import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './core/guards/auth.guard.service';
import {UnauthorizedComponent} from './auth/components/unauthorized/unauthorized.component';
import {IndexComponent} from './home/components/index/index.component';
import {Univers} from './shared/enums/univers';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: '',
    component: IndexComponent
  },
  {
    path: Univers.ADMIN.url,
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
    canActivate: [AuthGuard],
    data: {
      allowedRoles: Univers.ADMIN.roles
    }
  },
  {
    path: Univers.USERS.url,
    loadChildren: () => import('./user/user.module').then(m => m.UserModule),
    canActivate: [AuthGuard],
    data: {
      allowedRoles: Univers.USERS.roles
    }
  },
  {
    path: Univers.JOBS.url,
    loadChildren: () => import('./job/job.module').then(m => m.JobModule),
    canActivate: [AuthGuard],
    data: {
      allowedRoles: Univers.JOBS.roles
    }
  },
  {
    path: 'unauthorized',
    component: UnauthorizedComponent
  },
  {
    path: Univers.DISCUSSION.url,
    loadChildren: () => import('./blog/blog.module').then(m => m.BlogModule),
    canActivate: [AuthGuard],
    data: {
      allowedRoles: Univers.DISCUSSION.roles
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
