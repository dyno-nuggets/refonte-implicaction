import {Component} from '@angular/core';
import {SidebarService} from '../../shared/services/sidebar.service';
import {JobPostingFormComponent} from './components/job-posting-form/job-posting-form.component';
import {JobPosting} from '../../shared/models/job-posting';
import {JobService} from '../../job/services/job.service';
import {ToasterService} from '../../core/services/toaster.service';
import {JobFilterContextService} from '../../job/services/job-filter-context.service';
import {ActivatedRoute} from '@angular/router';
import {JobsListComponent} from '../../job/components/jobs-list/jobs-list.component';
import {finalize, take} from 'rxjs/operators';

@Component({
  selector: 'app-job',
  templateUrl: './admin-jobs.component.html',
  styleUrls: ['./admin-jobs.component.scss']
})
export class AdminJobsComponent extends JobsListComponent {

  isArchiveEnabled: boolean;
  isArchive: boolean;
  selectedJobs: JobPosting[] = [];

  constructor(
    protected toastService: ToasterService,
    protected jobService: JobService,
    protected filterService: JobFilterContextService,
    protected sidebarService: SidebarService,
    protected route: ActivatedRoute
  ) {
    super(toastService, jobService, filterService, sidebarService, route);
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

  toggleArchiveJob(job: JobPosting): void {
    this.jobService
      .archiveJob(job.id)
      .subscribe(
        () => this.paginate(),
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Succès', job.archive ? 'Offre désarchivée' : `Offre archivée`)
      );
  }

  toggleArchiveSelectedJobs(): void {
    const jobsId = this.selectedJobs.map(job => job.id);
    this.jobService
      .toggleArchiveJobs(jobsId)
      .subscribe(
        () => this.paginate(),
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Succès', 'Opération effectuée'),
      );
    this.selectedJobs = [];
    this.isArchiveEnabled = false;
  }

  onRowSelected(): void {
    // on vérifie que tous les éléments sélectionné sont tous archivés ou désarchivés
    this.isArchiveEnabled = this.selectedJobs.length && this.selectedJobs.every(job => job.archive === this.selectedJobs[0].archive);
    // on recherche si le bouton toggleArchive doit archiver ou désarchiver
    this.isArchive = this.selectedJobs.length && this.selectedJobs[0].archive;
  }

  protected innerPaginate(): void {
    this.jobService
      .getAllByCriteria(this.pageable, this.criteria)
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
