import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoggedInAuthGuard} from './guards/logged-in-auth.guard';
import {LoginPageComponent} from './pages/login-page/login-page.component';
import {LogoutPageComponent} from './pages/logout-page/logout-page.component';
import {SignupPageComponent} from './pages/signup-page/signup-page.component';
import {InitializePageComponent} from './pages/initialize-page/initialize-page.component';
import {AppInitializedGuard} from '../core/guards/app-initialized.guard';

const routes: Routes = [
  {path: 'sign-up', component: SignupPageComponent, canActivate: [AppInitializedGuard, LoggedInAuthGuard]},
  {path: 'login', component: LoginPageComponent, canActivate: [AppInitializedGuard, LoggedInAuthGuard]},
  {path: 'logout', component: LogoutPageComponent},
  {path: 'initialize', component: InitializePageComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
