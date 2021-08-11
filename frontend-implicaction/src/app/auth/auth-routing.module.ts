import {NgModule} from '@angular/core';
import {SignupComponent} from './components/signup/signup.component';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';

const routes: Routes = [
  {path: 'sign-up', component: SignupComponent},
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
