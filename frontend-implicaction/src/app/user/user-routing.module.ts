import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {UserListComponent} from './components/user-list/user-list.component';


const routes: Routes = [
  {
    path: ':userId/profile',
    component: UserProfileComponent
  },
  {
    path: 'friends/received',
    component: UserListComponent,
  },
  {
    path: 'friends/sent',
    component: UserListComponent,
  },
  {
    path: 'friends',
    component: UserListComponent,
  },
  {
    path: '',
    component: UserListComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {
}
