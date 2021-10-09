import {Component, OnInit} from '@angular/core';
import {Constants} from '../../../config/constants';
import {finalize} from 'rxjs/operators';
import {ToasterService} from '../../../core/services/toaster.service';
import {JobService} from '../../services/job.service';
import {JobSortEnum} from '../../enums/job-sort.enum';

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
  selectedOrder = JobSortEnum.DATE_DESC;
  searchKey = '';

  constructor(
    private toastService: ToasterService,
    private jobsService: JobService,
  ) {

  }

  ngOnInit(): void {
    this.pageable.sortOrder = this.selectedOrder.sortOrder;
    this.pageable.sortBy = this.selectedOrder.sortBy;

    this.paginate({
      first: 0,
      rows: this.ROWS_PER_PAGE_OPTIONS[0],
      page: this.pageable.page
    });
  }

  paginate({first, rows, page} = this.pageable): void {
    this.isLoading = true;
    this.pageable.page = page;
    this.pageable.rows = rows;
    this.pageable.first = first;
    this.jobsService
      .getAll(this.pageable, this.searchKey)
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

  onFilterChange({value}): void {
    const filterEnum = JobSortEnum.from(value);
    this.pageable.sortBy = filterEnum.sortBy;
    this.pageable.sortOrder = filterEnum.sortOrder;
    this.paginate();
  }
}
