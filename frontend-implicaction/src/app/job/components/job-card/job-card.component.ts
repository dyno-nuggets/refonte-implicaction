import {Component, Input} from '@angular/core';
import {JobPosting} from '../../../shared/models/job-posting';
import {Univers} from '../../../shared/enums/univers';
import {ApplyStatusCode} from '../../../board/enums/apply-status-enum';
import {JobApplicationService} from '../../../board/services/job-application.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.scss']
})
export class JobCardComponent {

  @Input()
  job: JobPosting;

  univers = Univers;
  isLoading = false;

  constructor(
    private jobBoardService: JobApplicationService,
    private toasterService: ToasterService
  ) {
  }

  addToJobBoard(): void {
    this.isLoading = true;
    this.jobBoardService
      .createApplication({jobId: this.job.id, status: ApplyStatusCode.PENDING})
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        () => this.job.apply = true,
        error => {
          this.job.apply = false;
          const message = error.status === 403 ?
            'Le job board est réservé aux adhérents' : `Une erreur est survenue lors de l'ajout de l'offre à votre board`;
          this.toasterService.error('Oops', message);
        }
      );
  }
}
