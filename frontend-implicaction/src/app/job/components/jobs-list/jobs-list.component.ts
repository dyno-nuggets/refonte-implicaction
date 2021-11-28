import {Component, OnInit} from '@angular/core';
import {Constants} from '../../../config/constants';
import {finalize} from 'rxjs/operators';
import {ToasterService} from '../../../core/services/toaster.service';
import {JobService} from '../../services/job.service';
import {JobSortEnum} from '../../enums/job-sort.enum';
import {JobCriteriaFilter} from '../../models/job-criteria-filter';
import {JobFilterContextService} from '../../services/job-filter-context.service';
import {ActivatedRoute} from '@angular/router';
import {SortDirectionEnum} from '../../../shared/enums/sort-direction.enum';
import {BaseWithPaginationComponent} from '../../../shared/components/base-with-pagination/base-with-pagination.component';
import {JobPosting} from '../../../shared/models/job-posting';

@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.scss']
})
export class JobsListComponent extends BaseWithPaginationComponent<JobPosting, JobCriteriaFilter> implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;

  isLoading = true;

  // Pagination et filtres
  orderByEnums = JobSortEnum.all();
  selectedOrderCode: string;
  sortDirection = SortDirectionEnum;

  constructor(
    private toastService: ToasterService,
    private jobsService: JobService,
    private filterService: JobFilterContextService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  ngOnInit(): void {
    this.pageable.sortOrder = JobSortEnum.DATE_DESC.sortDirection;
    this.pageable.sortBy = JobSortEnum.DATE_DESC.sortBy;
    this.selectedOrderCode = JobSortEnum.DATE_DESC.code;

    // réinitialisation systématique du filtre au chargement du composant
    this.filterService.criteria = {};

    this.filterService
      .observe()
      .subscribe(criteria => {
        this.criteria = criteria;
        const objectParam = this.buildQueryParams();
        this.filterService.updateRouteQueryParams(objectParam);
        this.paginate();
      });

    this.getFilterFromQueryParams(['keyword'])
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

  protected innerPaginate(): void {
    this.jobsService
      .getAllByCriteria(this.pageable, this.criteria, true)
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
}
