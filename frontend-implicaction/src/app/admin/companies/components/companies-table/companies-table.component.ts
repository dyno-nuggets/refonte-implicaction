import {Component, OnInit} from '@angular/core';
import {Pageable} from '../../../../shared/models/pageable';
import {finalize, take} from 'rxjs/operators';
import {Company} from '../../../../shared/models/company';
import {Constants} from '../../../../config/constants';
import {CompanyService} from '../../../../company/services/company.service';
import {ToasterService} from '../../../../core/services/toaster.service';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {CompaniesFormComponent} from '../companies-form/companies-form.component';
import {CompanyContextServiceService} from '../../../../shared/services/company-context-service.service';
import {BaseWithPaginationComponent} from '../../../../shared/components/base-with-pagination/base-with-pagination.component';

@Component({
  selector: 'app-companies-table',
  templateUrl: './companies-table.component.html',
  styleUrls: ['./companies-table.component.scss']
})
export class CompaniesTableComponent extends BaseWithPaginationComponent<Company> implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;
  loading = true; // indique si les données sont en chargement

  // Pagination
  pageable: Pageable = Constants.PAGEABLE_DEFAULT;

  constructor(
    private companyService: CompanyService,
    private toastService: ToasterService,
    private sidebarService: SidebarService,
    private companyContextService: CompanyContextServiceService
  ) {
    super();
  }

  ngOnInit(): void {
    this.companyContextService
      .observe$
      .subscribe(company => {
        if (company) {
          this.paginate();
        }
      });
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

  onAddCompany(): void {
    this.sidebarService
      .open({
        title: 'Ajouter une entreprise',
        component: CompaniesFormComponent,
        width: 650
      });
  }

  protected innerPaginate(): void {
    // FIXME: il faut forcer les paramètres de tri car ils restent définis avec les valeurs du dernier composant visité
    this.pageable.sortBy = 'id';
    this.pageable.sortOrder = 'ASC';
    this.companyService
      .getAll(this.pageable)
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
}

