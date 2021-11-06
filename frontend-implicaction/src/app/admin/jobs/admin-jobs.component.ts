import {Component} from '@angular/core';
import {SidebarService} from '../../shared/services/sidebar.service';
import {JobPostingFormComponent} from './components/job-posting-form/job-posting-form.component';

@Component({
  selector: 'app-job',
  templateUrl: './admin-jobs.component.html',
  styleUrls: ['./admin-jobs.component.scss']
})
export class AdminJobsComponent {

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

}
