import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UsersComponent} from './users.component';
import {UsersRoutingModule} from './users-routing.module';
import {TableModule} from 'primeng/table';
import {InputTextModule} from 'primeng/inputtext';
import {TableUsersComponent} from './components/users-table/table-users.component';
import {PendingUserTableComponent} from './components/pending-user-table/pending-user-table.component';
import {CheckboxModule} from 'primeng/checkbox';
import {FormsModule} from '@angular/forms';
import {FeatherModule} from 'angular-feather';


@NgModule({
  declarations: [
    UsersComponent,
    TableUsersComponent,
    PendingUserTableComponent
  ],
  exports: [
    PendingUserTableComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule,
    TableModule,
    InputTextModule,
    CheckboxModule,
    FormsModule,
    FeatherModule,
  ]
})
export class UsersModule {
}
