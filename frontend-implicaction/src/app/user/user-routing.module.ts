import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {UserListComponent} from './components/user-list/user-list.component';
import {FriendsOverviewComponent} from './components/friends-overview/friends-overview.component';


const routes: Routes = [
  {
    path: ':userId/profile',
    component: UserProfileComponent
  },
  {
    path: ':userId/friends',
    component: FriendsOverviewComponent
  },
  {
    path: 'list',
    component: UserListComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {
}
