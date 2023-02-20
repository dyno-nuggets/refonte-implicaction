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
    path: Univers.PROFILE.url,
    loadChildren: () => import('./profile/profile.module').then(m => m.ProfileModule),
    canActivate: [AuthGuard],
    data: {
      allowedRoles: Univers.PROFILE.roles
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
  },
  {
    path: Univers.FORUM.url,
    loadChildren: () => import('./forum/forum.module').then(m => m.ForumModule),
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
