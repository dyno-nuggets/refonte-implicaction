import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './core/guards/auth.guard.service';
import {UnauthorizedComponent} from './auth/pages/unauthorized/unauthorized.component';
import {Univers} from './shared/enums/univers';
import {BoardComponent} from './board/board.component';
import {CompanyAreaComponent} from "./company-area/company-area.component";

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
    path: Univers.COMMUNITY.url,
    loadChildren: () => import('./community/community.module').then(m => m.CommunityModule),
    canActivate: [AuthGuard],
    data: {
      allowedRoles: Univers.COMMUNITY.roles
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
    path: Univers.BOARD.url,
    component: BoardComponent,
    canActivate: [AuthGuard],
    data: {
      allowedRoles: Univers.BOARD.roles
    }
  },
  {
    path: Univers.COMPANY_AREA.url,
    component: CompanyAreaComponent,
  },
  {
    path: Univers.FORUM.url,
    loadChildren: () => import('./forum/forum.module').then(m => m.ForumModule),
    canActivate: [AuthGuard],
    data: {
      allowedRoles: Univers.FORUM.roles
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
