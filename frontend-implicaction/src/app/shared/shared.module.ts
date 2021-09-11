import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './components/header/header.component';
import {RouterModule} from '@angular/router';
import {BadgeModule} from 'primeng/badge';
import {IconsModule} from '../icons/icons.module';


@NgModule({
  declarations: [
    HeaderComponent,
  ],
  exports: [
    HeaderComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    BadgeModule,
    IconsModule
  ]
})
export class SharedModule {
}
