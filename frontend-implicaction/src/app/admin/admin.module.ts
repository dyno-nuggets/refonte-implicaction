import {LOCALE_ID, NgModule} from '@angular/core';
import {CommonModule, registerLocaleData} from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import {AdminRoutingModule} from './admin-routing.module';
import {AdminComponent} from './admin.component';
import {SharedModule} from './shared/shared.module';
import {CompaniesComponent} from './companies/companies.component';
import {CompaniesTableComponent} from './companies/components/companies-table/companies-table.component';
import {TableModule} from 'primeng/table';


@NgModule({
  declarations: [
    AdminComponent,
    CompaniesComponent,
    CompaniesTableComponent,
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule,
    TableModule
  ],
  providers: [{provide: LOCALE_ID, useValue: 'fr'}],
})
export class AdminModule {
  constructor() {
    registerLocaleData(localeFr, 'fr'); // passage du format de date en français
  }
}
