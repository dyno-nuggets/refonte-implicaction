import {Component} from '@angular/core';
import {Pageable} from '../../../../shared/models/pageable';
import {Constants} from '../../../../config/constants';
import {ToasterService} from '../../../../core/services/toaster.service';
import {JobService} from '../../../../job/services/job.service';
import {LazyLoadEvent} from 'primeng/api';
import {finalize, take} from 'rxjs/operators';
import {JobSortEnum} from '../../../../job/enums/job-sort.enum';
import {JobCriteriaFilter} from '../../../../job/models/job-criteria-filter';

@Component({
  selector: 'app-jobs-table',
  templateUrl: './jobs-table.component.html',
  styleUrls: ['./jobs-table.component.scss']
})
export class JobsTableComponent {

  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;
  loading = true; // indique si les données sont en chargement

  // Pagination
  pageable: Pageable = Constants.PAGEABLE_DEFAULT;
  orderByEnums = JobSortEnum.all();
  selectedOrder = JobSortEnum.DATE_DESC;
  jobCriteria: JobCriteriaFilter;

  constructor(
    private jobService: JobService,
    private toastService: ToasterService,
  ) {
  }

  loadJobs(event: LazyLoadEvent): void {
    this.loading = true;
    const page = event.first / event.rows;

    this.jobService
      .getAllByCriteria({page, rows: event.rows}, this.jobCriteria)
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
}
