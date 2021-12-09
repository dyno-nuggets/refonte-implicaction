import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PaginatorModule} from 'primeng/paginator';
import {SharedModule} from '../shared/shared.module';
import {CompanyRoutingModule} from './company-routing.module';
import {FeatherModule} from 'angular-feather';
import {BusinessAreaComponent} from './components/business-area/business-area.component';


@NgModule({
  declarations: [
    BusinessAreaComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    CompanyRoutingModule,
    FeatherModule,
    PaginatorModule,
  ]
})
export class CompanyModule {
}
