import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserProfilePageComponent} from "./pages/user-profile-page/user-profile-page.component";


const routes: Routes = [
  {
    path: ':username',
    component: UserProfilePageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule {
}
