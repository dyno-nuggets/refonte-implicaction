import {Component, OnInit} from '@angular/core';
import {JobPosting} from '../../../shared/models/job-posting';
import {JobService} from '../../services/job.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {ActivatedRoute} from '@angular/router';
import {Constants} from '../../../config/constants';
import {ApplyStatusCode} from '../../../board/enums/apply-status-enum';
import {JobApplicationService} from '../../../board/services/job-application.service';

@Component({
  selector: 'app-job-details',
  templateUrl: './job-details.component.html',
  styleUrls: ['./job-details.component.scss']
})
export class JobDetailsComponent implements OnInit {

  job: JobPosting = {};
  constant = Constants;

  constructor(
    private jobService: JobService,
    private toasterService: ToasterService,
    private route: ActivatedRoute,
    private jobBoardService: JobApplicationService
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
        const jobId = paramMap.get('jobId');
        this.jobService
          .getById(jobId)
          .subscribe(
            job => this.job = job,
            () => this.toasterService.error('oops', 'Une erreur est survenue !')
          );
      }
    );
  }

  addToJobBoard(): void {
    this.jobBoardService
      .createApplication({jobId: this.job.id, status: ApplyStatusCode.PENDING})
      .subscribe(
        () => this.job.apply = true,
        () => {
          this.job.apply = false;
          this.toasterService.error('Oops', `Une erreur est survenue lors de l'ajout de l'offre à votre board`);
        },
        () => this.toasterService.success('Succès', `L'offre a bien été ajoutée au job board.`)
      );
  }
}
