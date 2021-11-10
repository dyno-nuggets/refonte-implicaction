import {Component, OnInit} from '@angular/core';
import {Pageable} from '../../../../shared/models/pageable';
import {finalize, take} from 'rxjs/operators';
import {Company} from '../../../../shared/models/company';
import {Constants} from '../../../../config/constants';
import {CompanyService} from '../../../../company/services/company.service';
import {ToasterService} from '../../../../core/services/toaster.service';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {CompaniesFormComponent} from '../companies-form/companies-form.component';
import {CompanyFilterContextService} from '../../../../company/services/company-filter-context.service';
import {CompanyCriteriaFilter} from '../../../../job/models/company-criteria-filter';
import {ActivatedRoute} from '@angular/router';
import {CompanySortEnum} from '../../../../company/enums/company-sort.enum';
import {SortDirectionEnum} from '../../../../shared/enums/sort-direction.enum';
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
  orderByEnums = CompanySortEnum.all();
  criteria: CompanyCriteriaFilter;
  selectedOrder = CompanySortEnum.NAME_ASC;
  selectedOrderCode: string;
  sortDirection = SortDirectionEnum;

  constructor(
    private companyService: CompanyService,
    private toastService: ToasterService,
    private sidebarService: SidebarService,
    private companyContextService: CompanyContextServiceService,
    private filterService: CompanyFilterContextService,
    private route: ActivatedRoute
  ) {
    super();
  }

  ngOnInit(): void {
    this.pageable.sortOrder = CompanySortEnum.NAME_ASC.sortDirection;
    this.pageable.sortBy = CompanySortEnum.NAME_ASC.sortBy;
    this.selectedOrderCode = CompanySortEnum.NAME_ASC.code;

    this.companyContextService
      .observe$
      .subscribe(company => {
        if (company) {
          this.paginate();
        }
      });

    this.getFilterFromQueryParams()
      .then(() => this.filterService.setFilter(this.criteria));
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
      .getAllByCriteria(this.pageable, this.criteria)
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

  private async getFilterFromQueryParams(): Promise<void> {
    // TODO: voir si y'a un moyen plus élégant avec typeof
    const filterKeys = ['name'];
    const pageableKeys = ['rows', 'page', 'sortOrder', 'sortBy'];
    return new Promise(resolve => {
      this.route
        .queryParams
        .subscribe(params => {
          Object.entries(params)
            .forEach(([key, value]) => {
              if (filterKeys.includes(key)) {
                this.criteria[key] = value;
              } else if (pageableKeys.includes(key)) {
                this.pageable[key] = value;
              }
            });
          return resolve();
        });
    });
  }

  /**
   * @return any les filtres de recherche auxquels sont ajoutés les filtres de pagination
   */
  private buildQueryParams(): any {
    return {
      ...this.criteria,
      size: this.pageable.rows,
      page: this.pageable.page,
      sortBy: this.pageable.sortBy,
      sortOrder: this.pageable.sortOrder
    };
  }
}

