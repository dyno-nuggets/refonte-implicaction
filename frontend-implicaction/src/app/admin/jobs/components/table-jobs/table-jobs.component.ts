import {Component} from '@angular/core';
import {ToasterService} from '../../../../core/services/toaster.service';
import {finalize, take} from 'rxjs/operators';
import {LazyLoadEvent} from 'primeng/api';
import {Pageable} from '../../../../shared/models/pageable';
import {Constants} from '../../../../config/constants';
import {JobService} from '../../../../job/services/job.service';
import {SidebarService} from '../../../../shared/services/sidebar.service';
import {JobPostingFormComponent} from '../job-posting-form/job-posting-form.component';
import {JobPosting} from '../../../../shared/models/job-posting';

@Component({
  selector: 'app-table-jobs',
  templateUrl: './table-jobs.component.html',
  styleUrls: ['./table-jobs.component.scss']
})
export class TableJobsComponent {

  loading = true; // indique si les données sont en chargement

  // Pagination
  pageable: Pageable = Constants.PAGEABLE_DEFAULT;
  rowsPerPage = this.pageable.rowsPerPages[0];

  constructor(
    private jobService: JobService,
    private toastService: ToasterService,
    private sidebarService: SidebarService
  ) {
  }

  loadOffers(event: LazyLoadEvent): void {

    this.loading = true;
    const page = event.first / event.rows;

    this.jobService
      .getAllByCriteria({page, rows: event.rows}, {})
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

  onAddJobPosting(): void {
    this.sidebarService
      .open({
        title: 'Ajouter une nouvelle offre d\'emploi',
        component: JobPostingFormComponent,
        width: 650
      });
  }

  editJob(job: JobPosting): void {
    this.sidebarService
      .open({
        title: `Editer une nouvelle offre d'emploi`,
        input: {job},
        component: JobPostingFormComponent,
        width: 650
      });
  }

}
