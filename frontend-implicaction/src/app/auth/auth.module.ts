import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SignupComponent} from './pages/signup/signup.component';
import {AuthRoutingModule} from './auth-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import {LoginComponent} from './pages/login/login.component';
import {UnauthorizedComponent} from './pages/unauthorized/unauthorized.component';
import {SharedModule} from '../shared/shared.module';
import {AuthFormComponent} from './components/auth-form/auth-form.component';


@NgModule({
  declarations: [
    SignupComponent,
    LoginComponent,
    UnauthorizedComponent,
    AuthFormComponent
  ],
  exports: [
    SignupComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    SharedModule,
  ]
})
export class AuthModule {
}
