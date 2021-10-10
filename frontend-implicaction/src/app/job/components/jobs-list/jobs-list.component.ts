import {Component, OnInit} from '@angular/core';
import {Constants} from '../../../config/constants';
import {finalize} from 'rxjs/operators';
import {ToasterService} from '../../../core/services/toaster.service';
import {JobService} from '../../services/job.service';
import {JobSortEnum} from '../../enums/job-sort.enum';
import {FilterContextService} from '../../../shared/services/filter-context.service';
import {CriteriaFilter} from '../../../shared/models/criteria-filter';

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
  criteria: CriteriaFilter = {};
  selectedOrderCode: string;

  constructor(
    private toastService: ToasterService,
    private jobsService: JobService,
    private filterContextService: FilterContextService
  ) {
  }

  ngOnInit(): void {
    this.pageable.sortOrder = JobSortEnum.DATE_DESC.sortOrder;
    this.pageable.sortBy = JobSortEnum.DATE_DESC.sortBy;

    this.filterContextService
      .observeFilter()
      .subscribe(criteria => {
        this.criteria = criteria;
        this.paginate();
      });

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

  onSearchChange(): void {
    this.filterContextService.setFilter(this.criteria);
  }
}
