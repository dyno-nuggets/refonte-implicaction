import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GestionRolesComponent} from './gestion-roles.component';
import {gestionrolesRoutingModule} from './gestion-roles-routing.module';
import {FormsModule} from '@angular/forms';
import {NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';
import {MultiSelectModule} from "primeng/multiselect";
import {AutoCompleteModule} from "primeng/autocomplete";

@NgModule({
  declarations: [
    GestionRolesComponent,
  ],
  imports: [
    CommonModule,
    gestionrolesRoutingModule,
    FormsModule,
    NgMultiSelectDropDownModule,
    MultiSelectModule,
    AutoCompleteModule,

  ]
})
export class GestionrolesModule {
}
