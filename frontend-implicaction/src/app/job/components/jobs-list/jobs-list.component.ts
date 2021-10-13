import {Component, OnInit} from '@angular/core';
import {Constants} from '../../../config/constants';
import {finalize} from 'rxjs/operators';
import {ToasterService} from '../../../core/services/toaster.service';
import {JobService} from '../../services/job.service';
import {JobSortEnum} from '../../enums/job-sort.enum';
import {JobCriteriaFilter} from '../../models/job-criteria-filter';
import {JobFilterContextService} from '../../services/job-filter-context.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.scss']
})
export class JobsListComponent implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;

  isLoading = true;

  // Pagination et tri
  pageable = Constants.PAGEABLE_DEFAULT;
  orderByEnums = JobSortEnum.all();
  criteria: JobCriteriaFilter = {};
  selectedOrderCode: string;

  constructor(
    private toastService: ToasterService,
    private jobsService: JobService,
    private filterService: JobFilterContextService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.pageable.sortOrder = JobSortEnum.DATE_DESC.sortOrder;
    this.pageable.sortBy = JobSortEnum.DATE_DESC.sortBy;

    this.filterService
      .observeFilter()
      .subscribe(criteria => {
        this.criteria = criteria;
        this.paginate();
      });

    this.route.queryParams
      .subscribe(params => {
          console.log(params);
        }
      );

    this.pageable.sortBy = JobSortEnum.DATE_DESC.sortBy;
    this.pageable.sortOrder = JobSortEnum.DATE_DESC.sortOrder;
    this.selectedOrderCode = JobSortEnum.DATE_DESC.code;
  }

  paginate(): void {
    this.isLoading = true;
    this.jobsService
      .getAllByCriteria(this.pageable, this.criteria)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.rows = data.size;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
          this.router.navigate(
            [],
            {
              relativeTo: this.route,
              queryParams: {},
              queryParamsHandling: 'merge'
            })
          ;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des offres')
      );
  }

  onSortChange({value}): void {
    const selectedOrderField = JobSortEnum.from(value);
    this.pageable.sortBy = selectedOrderField.sortBy;
    this.pageable.sortOrder = selectedOrderField.sortOrder;
    this.paginate();
  }

  navigateToFoo(): void {
    // changes the route without moving from the current view or
    // triggering a navigation event,
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {...this.criteria},
      queryParamsHandling: 'merge',
      // preserve the existing query params in the route
      skipLocationChange: true
      // do not trigger navigation
    });
  }

  onSearchChange(): void {
    this.filterService.setFilter(this.criteria);
  }
}
