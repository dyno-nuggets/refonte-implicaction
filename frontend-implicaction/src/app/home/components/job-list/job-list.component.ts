import {Component, OnInit} from '@angular/core';
import {ToasterService} from '../../../core/services/toaster.service';
import {JobService} from '../../../job/services/job.service';
import {JobPosting} from '../../../shared/models/job-posting';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.scss']
})
export class JobListComponent implements OnInit {

  lastJobs: JobPosting[];

  constructor(
    private jobService: JobService,
    private toasterService: ToasterService
  ) {
  }

  ngOnInit(): void {
    this.jobService
      .getLastJobs(5)
      .subscribe(
        jobs => this.lastJobs = jobs,
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données'),
        () => this.toasterService.success('Ok', 'Le changement des données a bien été effectué')
      );
  }

}
