import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BusinessAreaComponent} from './components/business-area/business-area.component';

const routes: Routes = [
  {
    path: '',
    component: BusinessAreaComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompanyRoutingModule {
}
