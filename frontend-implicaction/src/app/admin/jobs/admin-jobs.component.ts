import {Component, OnInit} from '@angular/core';
import {SidebarService} from '../../shared/services/sidebar.service';
import {JobPostingFormComponent} from './components/job-posting-form/job-posting-form.component';
import {BaseWithPaginationComponent} from '../../shared/components/base-with-pagination/base-with-pagination.component';
import {JobPosting} from '../../shared/models/job-posting';
import {JobCriteriaFilter} from '../../job/models/job-criteria-filter';
import {Constants} from '../../config/constants';
import {Pageable} from '../../shared/models/pageable';
import {JobSortEnum} from '../../job/enums/job-sort.enum';
import {SortDirectionEnum} from '../../shared/enums/sort-direction.enum';
import {JobService} from '../../job/services/job.service';
import {ToasterService} from '../../core/services/toaster.service';
import {JobFilterContextService} from '../../job/services/job-filter-context.service';
import {ActivatedRoute} from '@angular/router';
import {finalize, take} from 'rxjs/operators';

@Component({
  selector: 'app-job',
  templateUrl: './admin-jobs.component.html',
  styleUrls: ['./admin-jobs.component.scss']
})
export class AdminJobsComponent extends BaseWithPaginationComponent<JobPosting, JobCriteriaFilter> implements OnInit {
  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;
  loading = true; // indique si les données sont en chargement
  isArchiveEnabled: boolean;
  isArchive: boolean;
  selectedJobs: JobPosting[] = [];

  // Pagination
  pageable: Pageable = Constants.PAGEABLE_DEFAULT;
  orderByEnums = JobSortEnum.all();
  selectedOrder = JobSortEnum.DATE_DESC;
  criteria: JobCriteriaFilter;
  selectedOrderCode: string;
  sortDirection = SortDirectionEnum;

  constructor(
    private jobService: JobService,
    private toastService: ToasterService,
    private sidebarService: SidebarService,
    private filterService: JobFilterContextService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  ngOnInit(): void {
    this.pageable.sortOrder = JobSortEnum.DATE_DESC.sortDirection;
    this.pageable.sortBy = JobSortEnum.DATE_DESC.sortBy;
    this.selectedOrderCode = JobSortEnum.DATE_DESC.code;

    this.filterService
      .observe()
      .subscribe(criteria => {
        this.criteria = criteria;
        const objectParam = this.buildQueryParams();
        this.filterService.updateRouteQueryParams(objectParam);
        this.paginate();
      });

    this.getFilterFromQueryParams(['search', 'contractType']).then(() => this.filterService.criteria = this.criteria);
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
    this.jobService
      .getAllByCriteria(this.pageable, this.criteria)
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

  archiveJobList(): void {
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

  onAddJob(): void {
    this.sidebarService
      .open({
        title: 'Ajouter une offre',
        component: JobPostingFormComponent,
        width: 650
      });
  }

  onRowSelected(): void {
    // on vérifie que tous les éléments sélectionné sont tous archivés ou désarchivés
    this.isArchiveEnabled = this.selectedJobs.every(job => job.archive === this.selectedJobs[0].archive);
    // on recherche si le bouton toggleArchive doit archiver ou désarchiver
    this.isArchive = this.selectedJobs.length > 0 ? this.selectedJobs[0].archive : false;

  }

}
