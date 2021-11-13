import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CompaniesRoutingModule} from './companies-routing.module';
import {CompaniesTableComponent} from './components/companies-table/companies-table.component';
import {CompaniesFormComponent} from './components/companies-form/companies-form.component';
import {ReactiveFormsModule} from '@angular/forms';
import {TableModule} from 'primeng/table';
import {EditorModule} from 'primeng/editor';
import {ToolbarModule} from 'primeng/toolbar';


@NgModule({
  declarations: [
    CompaniesTableComponent,
    CompaniesFormComponent
  ],
  exports: [
    CompaniesTableComponent
  ],
  imports: [
    CommonModule,
    CompaniesRoutingModule,
    ReactiveFormsModule,
    TableModule,
    EditorModule,
    ToolbarModule
  ]
})
export class CompaniesModule {
}
