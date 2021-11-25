import {Component, OnInit} from '@angular/core';
import {Pageable} from '../../../../shared/models/pageable';
import {Constants} from '../../../../config/constants';
import {ToasterService} from '../../../../core/services/toaster.service';
import {JobService} from '../../../../job/services/job.service';
import {LazyLoadEvent} from 'primeng/api';
import {finalize, take} from 'rxjs/operators';
import {JobSortEnum} from '../../../../job/enums/job-sort.enum';
import {JobCriteriaFilter} from '../../../../job/models/job-criteria-filter';
import {JobPosting} from '../../../../shared/models/job-posting';
import {JobPostingFormComponent} from '../job-posting-form/job-posting-form.component';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {SortDirectionEnum} from '../../../../shared/enums/sort-direction.enum';
import {JobFilterContextService} from '../../../../job/services/job-filter-context.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-jobs-table',
  templateUrl: './jobs-table.component.html',
  styleUrls: ['./jobs-table.component.scss']
})
export class JobsTableComponent implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;
  loading = true; // indique si les données sont en chargement

  selectedJobs: JobPosting[] = [];

  // Pagination
  pageable: Pageable = Constants.PAGEABLE_DEFAULT;
  orderByEnums = JobSortEnum.all();
  selectedOrder = JobSortEnum.DATE_DESC;
  criteria: JobCriteriaFilter;
  selectedOrderCode: string;
  sortDirection = SortDirectionEnum;

  constructor(
    private jobService: JobService,
    private toastService: ToasterService,
    private sidebarService: SidebarService,
    private filterService: JobFilterContextService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.filterService
      .observe()
      .subscribe(criteria => {
        this.criteria = criteria;
        const objectParam = this.buildQueryParams();
        this.filterService.updateRouteQueryParams(objectParam);
        this.paginate();
      });

    this.getFilterFromQueryParams()
      .then(() => this.filterService.criteria = this.criteria);
  }

  paginate({page, first, rows} = this.pageable): void {
    this.loading = true;
    this.pageable.page = page;
    this.pageable.first = first;
    this.pageable.rows = rows;
    this.jobService
      .getAllByCriteria(this.pageable, this.criteria)
      .pipe(finalize(() => this.loading = false))
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des offres')
      );
  }

  onSortChange({value}): void {
    const selectedOrderField = JobSortEnum.from(value);
    this.pageable.sortBy = selectedOrderField.sortBy;
    this.pageable.sortOrder = selectedOrderField.sortDirection;
    this.filterService.criteria = this.criteria; // on relance la recherche en updatant le filtre
  }

  onSearchChange(): void {
    this.filterService.criteria = this.criteria;
  }

  loadJobs(event: LazyLoadEvent): void {
    this.loading = true;
    const page = event.first / event.rows;

    this.jobService
      .getAllByCriteria({page, rows: event.rows}, this.criteria)
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

  editJob(job: JobPosting): void {
    this.sidebarService
      .open({
        title: `Editer une nouvelle offre d'emploi`,
        input: {job},
        component: JobPostingFormComponent,
        width: 650
      });
  }

  private async getFilterFromQueryParams(): Promise<void> {
    // TODO: voir si y'a un moyen plus élégant avec typeof
    const filterKeys = ['search', 'contractType'];
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
