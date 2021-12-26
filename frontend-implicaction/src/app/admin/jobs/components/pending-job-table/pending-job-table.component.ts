import {Component} from '@angular/core';
import {BaseWithPaginationAndFilterComponent} from '../../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {JobPosting} from '../../../../shared/models/job-posting';
import {ToasterService} from '../../../../core/services/toaster.service';
import {JobService} from '../../../../job/services/job.service';
import {ActivatedRoute} from '@angular/router';
import {finalize, take} from 'rxjs/operators';
import {Criteria} from '../../../../shared/models/Criteria';

@Component({
  selector: 'app-pending-job-table',
  templateUrl: './pending-job-table.component.html',
  styleUrls: ['./pending-job-table.component.scss']
})
export class PendingJobTableComponent extends BaseWithPaginationAndFilterComponent<JobPosting, Criteria> {

  isLoading = true;
  // Pagination et filtres
  rowsPerPage = this.pageable.rowsPerPages[0];

  constructor(
    private toastService: ToasterService,
    private jobsService: JobService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  validateJob(job: JobPosting): void {
    this.jobsService
      .validateJob(job.id)
      .subscribe(
        () => this.paginate(this.pageable),
        () => this.toastService.error('Oops', `Une erreur est survenue lors de la validation de l'offre.`),
        () => this.toastService.success('Succès', `L'offre  ${job.title} est désormais validée.`),
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
