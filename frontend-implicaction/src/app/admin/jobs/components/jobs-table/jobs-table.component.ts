import {Component, OnInit} from '@angular/core';
import {Constants} from '../../../../config/constants';
import {ToasterService} from '../../../../core/services/toaster.service';
import {JobService} from '../../../../job/services/job.service';
import {finalize} from 'rxjs/operators';
import {JobSortEnum} from '../../../../job/enums/job-sort.enum';
import {JobCriteriaFilter} from '../../../../job/models/job-criteria-filter';
import {JobPosting} from '../../../../shared/models/job-posting';
import {JobPostingFormComponent} from '../job-posting-form/job-posting-form.component';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {SortDirectionEnum} from '../../../../shared/enums/sort-direction.enum';
import {JobFilterContextService} from '../../../../job/services/job-filter-context.service';
import {ActivatedRoute} from '@angular/router';
import {BaseWithPaginationComponent} from '../../../../shared/components/base-with-pagination/base-with-pagination.component';

@Component({
  selector: 'app-jobs-table',
  templateUrl: './jobs-table.component.html',
  styleUrls: ['./jobs-table.component.scss']
})
export class JobsTableComponent extends BaseWithPaginationComponent<JobPosting, JobCriteriaFilter> implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;

  selectedJobs: JobPosting[] = [];
  isLoading = true;

  // Pagination et filtres
  orderByEnums = JobSortEnum.all();
  selectedOrderCode: string;
  sortDirection = SortDirectionEnum;

  constructor(
    private jobService: JobService,
    private toastService: ToasterService,
    private sidebarService: SidebarService,
    private filterService: JobFilterContextService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  ngOnInit(): void {
    this.pageable.sortOrder = JobSortEnum.DATE_DESC.sortDirection;
    this.pageable.sortBy = JobSortEnum.DATE_DESC.sortBy;
    this.selectedOrderCode = JobSortEnum.DATE_DESC.code;

    this.filterService.criteria = {};

    this.filterService
      .observe()
      .subscribe(criteria => {
        this.criteria = criteria;
        const objectParam = this.buildQueryParams();
        this.filterService.updateRouteQueryParams(objectParam);
        this.paginate();
      });

    this.getFilterFromQueryParams(['search', 'contractType'])
      .then(() => this.filterService.criteria = this.criteria);
  }

  protected innerPaginate(): void {
    this.jobService
      .getAllByCriteria(this.pageable, this.criteria)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.rows = data.size;
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

  editJob(job: JobPosting): void {
    this.sidebarService
      .open({
        title: `Editer une nouvelle offre d'emploi`,
        input: {job},
        component: JobPostingFormComponent,
        width: 650
      });
  }

  archiveJob(job: JobPosting): void {
    this.jobService
      .archiveJob(job.id)
      .subscribe(
        () => this.paginate(),
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Succès', job.archive ? 'Offre désarchivée' : `Offre archivée`)
      );
  }

  protected async getFilterFromQueryParams(): Promise<void> {
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

  archiveJobList(): void {
    let jobsId = this.selectedJobs.map(job => job.id);
    this.jobService
      .archiveJobList(jobsId)
      .subscribe(
        () => this.paginate(),
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Succès', ''),
      );
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
