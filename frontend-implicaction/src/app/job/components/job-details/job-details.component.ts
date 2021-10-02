import {Component, OnInit} from '@angular/core';
import {JobPosting} from '../../../shared/models/job-posting';
import {JobService} from '../../services/job.service';
import {ToasterService} from '../../../core/services/toaster.service';

@Component({
  selector: 'app-job-details',
  templateUrl: './job-details.component.html',
  styleUrls: ['./job-details.component.scss']
})
export class JobDetailsComponent implements OnInit {

  job: JobPosting = {};

  constructor(private jobService: JobService, private toasterService: ToasterService) {
  }

  ngOnInit(): void {
    this.jobService
      .getById('1')
      .subscribe(
        job => {
          this.job = job;
        },
        () => this.toasterService.error('oops', 'Une erreur est survenue !')
      );
  }

}
