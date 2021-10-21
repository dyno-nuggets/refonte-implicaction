import {Component} from '@angular/core';
import {Pageable} from '../../../../shared/models/pageable';
import {finalize, take} from 'rxjs/operators';
import {LazyLoadEvent} from 'primeng/api';
import {Company} from '../../../../shared/models/company';
import {Constants} from '../../../../config/constants';
import {CompanyService} from '../../../../company/services/company.service';
import {ToasterService} from '../../../../core/services/toaster.service';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {CompaniesFormComponent} from '../companies-form/companies-form.component';

@Component({
  selector: 'app-companies-table',
  templateUrl: './companies-table.component.html',
  styleUrls: ['./companies-table.component.scss']
})
export class CompaniesTableComponent {

  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;
  loading = true; // indique si les données sont en chargement

  // Pagination
  pageable: Pageable = Constants.PAGEABLE_DEFAULT;

  constructor(
    private companyService: CompanyService,
    private toastService: ToasterService,
    private sidebarService: SidebarService,
  ) {
  }

  loadCompanies(event: LazyLoadEvent): void {
    this.loading = true;
    const page = event.first / event.rows;

    this.companyService
      .getAll({page, rows: event.rows})
      .pipe(
        take(1),
        finalize(() => this.loading = false)
      )
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.rows = data.size;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération des données'),
      );
  }

  onEditCompany(company: Company): void {
    this.sidebarService
      .open({
        title: 'Editer une entreprise',
        component: CompaniesFormComponent,
        input: {company},
        width: 650
      });
  }
}

