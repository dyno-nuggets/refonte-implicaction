import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SignupComponent} from './pages/signup/signup.component';
import {AuthRoutingModule} from './auth-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import {UnauthorizedComponent} from './pages/unauthorized/unauthorized.component';
import {SharedModule} from '../shared/shared.module';
import {AuthFormWrapperComponent} from './components/base-auth-page/auth-form-wrapper.component';
import {LoginFormComponent} from './components/forms/login-form/login-form.component';
import {LoginPageComponent} from './pages/login-page/login-page.component';
import {LogoutPageComponent} from './pages/logout-page/logout-page.component';


@NgModule({
  declarations: [
    SignupComponent,
    LoginPageComponent,
    UnauthorizedComponent,
    AuthFormWrapperComponent,
    LogoutPageComponent,
    LoginFormComponent
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
