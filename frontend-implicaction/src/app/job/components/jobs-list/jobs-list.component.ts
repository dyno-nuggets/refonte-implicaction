import {Component, OnInit} from '@angular/core';
import {Constants} from '../../../config/constants';
import {JobPosting} from '../../../shared/models/job-posting';
import {finalize} from 'rxjs/operators';
import {ToasterService} from '../../../core/services/toaster.service';
import {JobService} from '../../services/job.service';

@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.scss']
})
export class JobsListComponent implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = [10, 25, 50];

  jobs: JobPosting[] = [];
  isLoading = true;

  // Pagination
  pageable = Constants.PAGEABLE_DEFAULT;

  constructor(
    private toastService: ToasterService,
    private jobsService: JobService,
  ) {

  }

  ngOnInit(): void {
    this.paginate({
      first: 0,
      rows: this.ROWS_PER_PAGE_OPTIONS[0],
    });
  }

  paginate({first, rows}): void {
    this.isLoading = true;
    this.pageable.page = first / rows;
    this.pageable.size = rows;
    this.jobsService
      .getAll(this.pageable)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.size = data.size;
          this.pageable.totalElements = data.totalElements;
          this.jobs = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des jobs')
      );
  }

}
