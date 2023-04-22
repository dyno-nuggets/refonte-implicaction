import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ProfileListPageComponent} from "./pages/profile-list-page/profile-list-page.component";
import {ProfileDetailsPageComponent} from "./pages/profile-details-page/profile-details-page.component";

const routes: Routes = [
  {
    path: 'relations/received',
    component: ProfileListPageComponent,
  },
  {
    path: 'relations/sent',
    component: ProfileListPageComponent,
  },
  {
    path: 'relations',
    component: ProfileListPageComponent,
  },
  {
    path: 'profile/:username',
    component: ProfileDetailsPageComponent
  },
  {
    path: '',
    component: ProfileListPageComponent
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
})
export class CommunityRoutingModule {
}
