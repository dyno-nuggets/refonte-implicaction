import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './components/header/header.component';
import {RouterModule} from '@angular/router';
import {BadgeModule} from 'primeng/badge';
import {UserCardComponent} from './components/user-card/user-card.component';


@NgModule({
  declarations: [
    HeaderComponent,
    UserCardComponent
  ],
  exports: [
    HeaderComponent,
    UserCardComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    BadgeModule
  ]
})
export class SharedModule {
}
