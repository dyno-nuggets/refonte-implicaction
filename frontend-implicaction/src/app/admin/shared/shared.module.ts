import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminMenuComponent} from './components/admin-menu/admin-menu.component';
import {RouterModule} from '@angular/router';


@NgModule({
  declarations: [
    AdminMenuComponent
  ],
  exports: [
    AdminMenuComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class SharedModule {
}
