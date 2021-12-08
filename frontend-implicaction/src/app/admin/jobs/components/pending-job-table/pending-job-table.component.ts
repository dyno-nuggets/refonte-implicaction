import {Component, OnInit} from '@angular/core';
import {BaseWithPaginationComponent} from '../../../../shared/components/base-with-pagination/base-with-pagination.component';
import {JobPosting} from '../../../../shared/models/job-posting';
import {Constants} from '../../../../config/constants';
import {JobSortEnum} from '../../../../job/enums/job-sort.enum';
import {SortDirectionEnum} from '../../../../shared/enums/sort-direction.enum';
import {ToasterService} from '../../../../core/services/toaster.service';
import {JobService} from '../../../../job/services/job.service';
import {ActivatedRoute} from '@angular/router';
import {finalize, take} from 'rxjs/operators';

@Component({
  selector: 'app-pending-job-table',
  templateUrl: './pending-job-table.component.html',
  styleUrls: ['./pending-job-table.component.scss']
})
export class PendingJobTableComponent extends BaseWithPaginationComponent<JobPosting, any> implements OnInit {
  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;

  isLoading = true;
  // Pagination et filtres
  orderByEnums = JobSortEnum.all();
  selectedOrderCode: string;
  sortDirection = SortDirectionEnum;
  rowsPerPage = this.pageable.rowsPerPages[0];

  constructor(
    private toastService: ToasterService,
    private jobsService: JobService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  ngOnInit(): void {
    this.paginate();
  }

  activateJob(job: JobPosting): void {
    this.jobsService
      .activateJob(job)
      .subscribe(
        () => {
          this.paginate({first: this.pageable.first, rows: this.pageable.rows});
        },
        () => this.toastService.error('Oops', `Une erreur est survenue lors de la validation de l'offre.`),
        () => this.toastService.success('Succès', `L'offre  ${job.title} est désormais active.`),
      );
  }

  protected innerPaginate(): void {
    this.jobsService
      .getAllPendingActivationJobs(this.pageable)
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
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération des données'),
      );
  }

}
