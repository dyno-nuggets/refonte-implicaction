import {Component, Input} from '@angular/core';
import {JobPosting} from '../../../../shared/models/job-posting';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
})
export class JobListComponent {

  @Input()
  latestJobs: JobPosting[] = [];

  @Input()
  isLoading = false;

  trackByJobId = (index: number, job: JobPosting) => job.id;

}
