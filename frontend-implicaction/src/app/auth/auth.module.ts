import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SignupComponent} from './components/signup/signup.component';
import {AuthRoutingModule} from './auth-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import {LoginComponent} from './components/login/login.component';
import {UnauthorizedComponent} from './components/unauthorized/unauthorized.component';
import {SharedModule} from '../shared/shared.module';


@NgModule({
  declarations: [
    SignupComponent,
    LoginComponent,
    UnauthorizedComponent
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
