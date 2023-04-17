import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoggedInAuthGuard} from './guards/logged-in-auth.guard';
import {LoginPageComponent} from './pages/login-page/login-page.component';
import {LogoutPageComponent} from './pages/logout-page/logout-page.component';
import {SignupPageComponent} from './pages/signup-page/signup-page.component';

const routes: Routes = [
  {path: 'sign-up', component: SignupPageComponent, canActivate: [LoggedInAuthGuard]},
  {path: 'login', component: LoginPageComponent, canActivate: [LoggedInAuthGuard]},
  {path: 'logout', component: LogoutPageComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
