import {NgModule} from '@angular/core';
import {SignupComponent} from './pages/signup/signup.component';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {LogoutComponent} from "./pages/logout/logout.component";
import {LoggedInAuthGuard} from "./guards/logged-in-auth.guard";

const routes: Routes = [
  {path: 'sign-up', component: SignupComponent, canActivate: [LoggedInAuthGuard]},
  {path: 'login', component: LoginComponent, canActivate: [LoggedInAuthGuard]},
  {path: 'logout', component: LogoutComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
