import {Component} from '@angular/core';
import {SidebarService} from '../../shared/services/sidebar.service';
import {CompaniesFormComponent} from './components/companies-form/companies-form.component';

@Component({
  selector: 'app-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.scss']
})
export class CompaniesComponent {
  constructor(
    private sidebarService: SidebarService,
  ) {
  }

  onAddCompany(): void {
    this.sidebarService
      .open({
        title: 'Ajouter une entreprise',
        component: CompaniesFormComponent,
        width: 650
      });
  }
}
