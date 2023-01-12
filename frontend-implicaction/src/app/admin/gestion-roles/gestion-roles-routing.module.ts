import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { GestionRolesComponent } from './gestion-roles.component';



const routes: Routes = [
  {path: '', component: GestionRolesComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class gestionrolesRoutingModule {
}
