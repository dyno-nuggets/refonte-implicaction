import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './components/header/header.component';
import {RouterModule} from '@angular/router';
import {BadgeModule} from 'primeng/badge';
import {IconsModule} from '../icons/icons.module';
import {LoadingComponent} from './components/loading/loading.component';
import {AlertComponent} from './components/alert/alert.component';
import {SidebarContentDirective} from './directives/sidebar-content.directive';


@NgModule({
  declarations: [
    HeaderComponent,
    LoadingComponent,
    AlertComponent
    SidebarContentDirective,
  ],
  exports: [
    HeaderComponent,
    LoadingComponent,
    AlertComponent
    SidebarContentDirective,
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
