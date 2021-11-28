import {Component, ViewChild} from '@angular/core';
import {SidebarService} from '../../shared/services/sidebar.service';
import {JobPostingFormComponent} from './components/job-posting-form/job-posting-form.component';
import {JobsTableComponent} from './components/jobs-table/jobs-table.component';

@Component({
  selector: 'app-job',
  templateUrl: './admin-jobs.component.html',
  styleUrls: ['./admin-jobs.component.scss']
})
export class AdminJobsComponent {
  @ViewChild(JobsTableComponent)
  jobsTableComponent: JobsTableComponent;

  constructor(
    private sidebarService: SidebarService,
  ) {
  }

  onAddJob(): void {
    this.sidebarService
      .open({
        title: 'Ajouter une offre',
        component: JobPostingFormComponent,
        width: 650
      });
  }

  archive(): void {
    this.jobsTableComponent.archiveJobList();
  }

}
