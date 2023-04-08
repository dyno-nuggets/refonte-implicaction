import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {SharedModule} from "../shared/shared.module";
import {HeaderComponent} from "./components/header/header.component";
import {NavbarBrandComponent} from './components/navbar-brand/navbar-brand.component';
import {NavbarNavComponent} from './components/navbar-nav/navbar-nav.component';
import {NavbarProfileDropdownComponent} from './components/navbar-profile-dropdown/navbar-profile-dropdown.component';
import {NavbarRegisterOptionsComponent} from './components/navbar-register-options/navbar-register-options.component';


@NgModule({
  declarations: [
    HeaderComponent,
    NavbarBrandComponent,
    NavbarNavComponent,
    NavbarProfileDropdownComponent,
    NavbarRegisterOptionsComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule
  ],
  exports: [
    HeaderComponent,
  ]
})
export class HeaderModule {
}
