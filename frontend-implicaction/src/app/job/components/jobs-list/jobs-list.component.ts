import {Component, OnInit} from '@angular/core';
import {Constants} from '../../../config/constants';
import {finalize} from 'rxjs/operators';
import {ToasterService} from '../../../core/services/toaster.service';
import {JobService} from '../../services/job.service';
import {JobSortEnum} from '../../enums/job-sort.enum';
import {JobCriteriaFilter} from '../../models/job-criteria-filter';
import {JobFilterContextService} from '../../services/job-filter-context.service';

@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.scss']
})
export class JobsListComponent implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;

  isLoading = true;

  // Pagination et filtres
  pageable = Constants.PAGEABLE_DEFAULT;
  orderByEnums = JobSortEnum.all();
  criteria: JobCriteriaFilter = {};
  selectedOrderCode: string;

  constructor(
    private toastService: ToasterService,
    private jobsService: JobService,
    private filterService: JobFilterContextService
  ) {
  }

  ngOnInit(): void {
    this.pageable.sortOrder = JobSortEnum.DATE_DESC.sortOrder;
    this.pageable.sortBy = JobSortEnum.DATE_DESC.sortBy;

    this.filterService
      .observeFilter()
      .subscribe(criteria => {
        this.criteria = criteria;
        const objectParam = this.buildQueryParams();
        this.filterService.updateRouteQueryParams(objectParam);
        this.paginate();
      });

    this.pageable.sortBy = JobSortEnum.DATE_DESC.sortBy;
    this.pageable.sortOrder = JobSortEnum.DATE_DESC.sortOrder;
    this.selectedOrderCode = JobSortEnum.DATE_DESC.code;
  }

  paginate({page, first, rows} = this.pageable): void {
    this.isLoading = true;
    this.pageable.page = page;
    this.pageable.first = first;
    this.pageable.rows = rows;
    this.jobsService
      .getAllByCriteria(this.pageable, this.criteria)
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

  onSortChange({value}): void {
    const selectedOrderField = JobSortEnum.from(value);
    this.pageable.sortBy = selectedOrderField.sortBy;
    this.pageable.sortOrder = selectedOrderField.sortOrder;
    this.filterService.setFilter(this.criteria); // on relance la recherche en updatant le filtre
  }

  onSearchChange(): void {
    this.filterService.setFilter(this.criteria);
  }

  /**
   * @return any les filtres de recherche auxquels sont ajoutés les filtres de pagination
   */
  private buildQueryParams(): any {
    return {
      ...this.criteria,
      rows: this.pageable.rows,
      page: this.pageable.page,
      sortBy: this.pageable.sortBy,
      sortOrder: this.pageable.sortOrder
    };
  }
}
