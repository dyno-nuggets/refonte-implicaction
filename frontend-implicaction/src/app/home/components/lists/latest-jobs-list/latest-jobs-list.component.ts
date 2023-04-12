import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {JobPosting} from '../../../../shared/models/job-posting';

@Component({
  selector: 'app-latest-jobs-list',
  templateUrl: './latest-jobs-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LatestJobsListComponent {

  @Input() latestJobs: JobPosting[] = [];
  @Input() isLoading = false;
  @Input() size: number;

  trackByJobId = (index: number, job: JobPosting) => job.id;

}
