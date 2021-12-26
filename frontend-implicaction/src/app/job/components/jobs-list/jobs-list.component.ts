import {Component, OnDestroy, OnInit} from '@angular/core';
import {finalize} from 'rxjs/operators';
import {ToasterService} from '../../../core/services/toaster.service';
import {JobService} from '../../services/job.service';
import {JobSortEnum} from '../../enums/job-sort.enum';
import {JobCriteriaFilter} from '../../models/job-criteria-filter';
import {JobFilterContextService} from '../../services/job-filter-context.service';
import {ActivatedRoute} from '@angular/router';
import {SortDirectionEnum} from '../../../shared/enums/sort-direction.enum';
import {BaseWithPaginationAndFilterComponent} from '../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {JobPosting} from '../../../shared/models/job-posting';
import {JobPostingFormComponent} from '../../../admin/jobs/components/job-posting-form/job-posting-form.component';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.scss']
})
export class JobsListComponent extends BaseWithPaginationAndFilterComponent<JobPosting, JobCriteriaFilter> implements OnInit, OnDestroy {

  isLoading = true;

  // Pagination et filtres
  orderByEnums = JobSortEnum.all();
  selectedOrderCode: string;
  sortDirection = SortDirectionEnum;
  subscription: Subscription;

  constructor(
    protected toastService: ToasterService,
    protected jobsService: JobService,
    protected filterService: JobFilterContextService,
    protected sidebarService: SidebarService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  ngOnInit(): void {
    this.pageable.sortOrder = JobSortEnum.DATE_DESC.sortDirection;
    this.pageable.sortBy = JobSortEnum.DATE_DESC.sortBy;
    this.selectedOrderCode = JobSortEnum.DATE_DESC.code;

    this.subscription = this.filterService
      .observe()
      .subscribe(criteria => {
        this.criteria = criteria;
        const objectParam = this.buildQueryParams();
        this.filterService.updateRouteQueryParams(objectParam);
        this.paginate();
      });

    this.getFilterFromQueryParams(['keyword', 'createdAt', 'contractType', 'businessSector'])
      .then(() => this.filterService.criteria = this.criteria);
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

  onAddJob(): void {
    this.sidebarService
      .open({
        title: 'Ajouter une offre',
        component: JobPostingFormComponent,
        width: 650
      });
  }

  protected innerPaginate(): void {
    this.jobsService
      .getAllValidatedByCriteria(this.pageable, this.criteria, false)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des offres')
      );
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
    this.filterService.criteria = {};
  }
}
