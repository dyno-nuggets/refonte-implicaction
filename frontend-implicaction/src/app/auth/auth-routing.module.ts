import {NgModule} from '@angular/core';
import {SignupComponent} from './components/signup/signup.component';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {path: 'sign-up', component: SignupComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
