import {NgModule} from '@angular/core';
import {SignupComponent} from './pages/signup/signup.component';
import {RouterModule, Routes} from '@angular/router';
import {LoggedInAuthGuard} from './guards/logged-in-auth.guard';
import {LoginPageComponent} from './pages/login-page/login-page.component';
import {LogoutPageComponent} from './pages/logout-page/logout-page.component';

const routes: Routes = [
  {path: 'sign-up', component: SignupComponent, canActivate: [LoggedInAuthGuard]},
  {path: 'login', component: LoginPageComponent, canActivate: [LoggedInAuthGuard]},
  {path: 'logout', component: LogoutPageComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
