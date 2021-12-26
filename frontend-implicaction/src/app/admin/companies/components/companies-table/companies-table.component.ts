import {Component, OnDestroy, OnInit} from '@angular/core';
import {finalize, take} from 'rxjs/operators';
import {Company} from '../../../../shared/models/company';
import {CompanyService} from '../../../../company/services/company.service';
import {ToasterService} from '../../../../core/services/toaster.service';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {CompaniesFormComponent} from '../companies-form/companies-form.component';
import {CompanyFilterContextService} from '../../../../company/services/company-filter-context.service';
import {ActivatedRoute} from '@angular/router';
import {CompanySortEnum} from '../../../../company/enums/company-sort.enum';
import {CompanyContextServiceService} from '../../../../shared/services/company-context-service.service';
import {BaseWithPaginationAndFilterComponent} from '../../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {Subscription} from 'rxjs';
import {Criteria} from '../../../../shared/models/Criteria';

@Component({
  selector: 'app-companies-table',
  templateUrl: './companies-table.component.html',
  styleUrls: ['./companies-table.component.scss']
})
export class CompaniesTableComponent extends BaseWithPaginationAndFilterComponent<Company, Criteria> implements OnInit, OnDestroy {

  selectedOrder = CompanySortEnum.NAME_ASC;
  selectedOrderCode: string;
  subscription: Subscription;
  selectedCompanies: Company[] = [];

  constructor(
    private companyService: CompanyService,
    private toastService: ToasterService,
    private sidebarService: SidebarService,
    private companyContextService: CompanyContextServiceService,
    private filterService: CompanyFilterContextService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  ngOnDestroy(): void {
    // désinscription et réinitialisation des filtres
    this.subscription?.unsubscribe();
    this.filterService.criteria = {};
  }

  ngOnInit(): void {
    this.pageable.sortOrder = CompanySortEnum.NAME_ASC.sortDirection;
    this.pageable.sortBy = CompanySortEnum.NAME_ASC.sortBy;
    this.selectedOrderCode = CompanySortEnum.NAME_ASC.code;

    // on s'abonne à l'ajout d'une nouvelle entreprise
    this.subscription = this.companyContextService
      .observe$
      .subscribe(company => {
        if (company) {
          this.paginate();
        }
      })
      // ... et à la modification des critères de recherche
      .add(
        this.filterService
          .observe()
          .subscribe(criteria => {
            this.criteria = criteria;
            const objectParam = this.buildQueryParams();
            this.filterService.updateRouteQueryParams(objectParam);
            this.paginate();
          })
      );

    this.getFilterFromQueryParams(['keyword']).then(() => this.filterService.criteria = this.criteria);
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
        finalize(() => this.isLoading = false)
      )
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.rows = data.size;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des compagnies')
      );
  }
}

