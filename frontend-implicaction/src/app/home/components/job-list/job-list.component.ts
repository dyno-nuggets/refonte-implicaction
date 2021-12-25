import {Component, OnInit} from '@angular/core';
import {ToasterService} from '../../../core/services/toaster.service';
import {JobService} from '../../../job/services/job.service';
import {JobPosting} from '../../../shared/models/job-posting';
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.scss']
})
export class JobListComponent implements OnInit {

  latestJobs: JobPosting[];

  constructor(
    private jobService: JobService,
    private toasterService: ToasterService
  ) {
  }

  ngOnInit(): void {
    this.jobService
      .getLatestJobs(Constants.LATEST_JOBS_COUNT)
      .subscribe(
        jobs => this.latestJobs = jobs,
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données')
      );
  }

  trackByJobId = (index: number, job: JobPosting) => job.id;

}
