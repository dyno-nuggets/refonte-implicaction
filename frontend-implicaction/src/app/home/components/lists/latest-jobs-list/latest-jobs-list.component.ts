import {Component, Input} from '@angular/core';
import {JobPosting} from '../../../../shared/models/job-posting';

@Component({
  selector: 'app-latest-jobs-list',
  templateUrl: './latest-jobs-list.component.html',
})
export class LatestJobsListComponent {

  @Input()
  latestJobs: JobPosting[] = [];

  @Input()
  isLoading = false;

  trackByJobId = (index: number, job: JobPosting) => job.id;

}
