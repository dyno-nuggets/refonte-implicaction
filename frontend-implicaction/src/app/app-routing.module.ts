import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './core/guards/auth.guard.service';
import {RoleEnum} from './shared/enums/role-enum.enum';
import {UnauthorizedComponent} from './auth/components/unauthorized/unauthorized.component';
import {IndexComponent} from './home/components/index/index.component';

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
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
    canActivate: [AuthGuard],
    data: {
      allowedRoles: [RoleEnum.ADMIN]
    }
  },
  {
    path: 'users',
    loadChildren: () => import('./user/user.module').then(m => m.UserModule),
    canActivate: [AuthGuard],
    data: {
      allowedRoles: [RoleEnum.USER, RoleEnum.ADMIN]
    }
  },
  {
    path: 'unauthorized',
    component: UnauthorizedComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
