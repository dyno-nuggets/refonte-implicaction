import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ProfileListPageComponent} from "./pages/profile-list-page/profile-list-page.component";
import {ProfileDetailsPageComponent} from "./pages/profile-details-page/profile-details-page.component";

const routes: Routes = [
  {
    path: 'profiles/:username',
    component: ProfileDetailsPageComponent
  },
  {
    path: 'profiles',
    component: ProfileListPageComponent
  },
  {
    path: '',
    redirectTo: 'profiles',
    pathMatch: 'full'
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
})
export class CommunityRoutingModule {
}
