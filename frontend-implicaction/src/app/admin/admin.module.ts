import {LOCALE_ID, NgModule} from '@angular/core';
import {CommonModule, registerLocaleData} from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import {AdminRoutingModule} from './admin-routing.module';
import {AdminComponent} from './admin.component';
import {SharedModule} from './shared/shared.module';
import {CompaniesComponent} from './companies/companies.component';
import {TableModule} from 'primeng/table';
import {CompaniesModule} from './companies/companies.module';
import {FeatherModule} from 'angular-feather';


@NgModule({
  declarations: [
    AdminComponent,
    CompaniesComponent,
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule,
    TableModule,
    CompaniesModule,
    FeatherModule
  ],
  providers: [{provide: LOCALE_ID, useValue: 'fr'}],
})
export class AdminModule {
  constructor() {
    registerLocaleData(localeFr, 'fr'); // passage du format de date en français
  }
}
