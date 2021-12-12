import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './core/guards/auth.guard.service';
import {UnauthorizedComponent} from './auth/components/unauthorized/unauthorized.component';
import {Univers} from './shared/enums/univers';
import {BoardComponent} from './board/board.component';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: Univers.HOME.url,
    loadChildren: () => import('./home/home.module').then(m => m.HomeModule)
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
    path: Univers.DISCUSSIONS.url,
    loadChildren: () => import('./discussion/discussion.module').then(m => m.DiscussionModule),
    canActivate: [AuthGuard],
    data: {
      allowedRoles: Univers.DISCUSSIONS.roles
    }
  },
  {
    path: Univers.BOARD.url,
    component: BoardComponent,
    canActivate: [AuthGuard],
    data: {
      allowedRoles: Univers.BOARD.roles
    }
  },
  {
    path: Univers.BUSINESS_AREA.url,
    loadChildren: () => import('./company/company.module').then(m => m.CompanyModule),
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
