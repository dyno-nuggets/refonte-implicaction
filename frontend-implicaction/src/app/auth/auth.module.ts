import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AuthRoutingModule} from './auth-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import {UnauthorizedComponent} from './pages/unauthorized/unauthorized.component';
import {SharedModule} from '../shared/shared.module';
import {AuthFormWrapperComponent} from './components/base-auth-page/auth-form-wrapper.component';
import {LoginFormComponent} from './components/forms/login-form/login-form.component';
import {LoginPageComponent} from './pages/login-page/login-page.component';
import {LogoutPageComponent} from './pages/logout-page/logout-page.component';
import {SignupFormComponent} from './components/forms/signup-form/signup-form.component';
import {SignupPageComponent} from './pages/signup-page/signup-page.component';


@NgModule({
  declarations: [
    SignupPageComponent,
    LoginPageComponent,
    UnauthorizedComponent,
    AuthFormWrapperComponent,
    LogoutPageComponent,
    LoginFormComponent,
    SignupFormComponent,
  ],
  exports: [
    SignupPageComponent
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
