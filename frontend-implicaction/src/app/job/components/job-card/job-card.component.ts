import {Component, Input} from '@angular/core';
import {JobPosting} from '../../../shared/models/job-posting';
import {Univers} from '../../../shared/enums/univers';
import {ApplyStatusCode} from '../../../board/enums/apply-status-enum';
import {JobBoardService} from '../../../board/services/job-board.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.scss']
})
export class JobCardComponent {

  @Input()
  job: JobPosting;

  univers = Univers;

  constructor(
    private jobBoardService: JobBoardService,
    private toasterService: ToasterService,
    private router: Router
  ) {
  }

  addToJobBoard(): void {
    this.jobBoardService
      .createApplication({jobId: this.job.id, status: ApplyStatusCode.PENDING})
      .subscribe(
        () => {
          this.router
            .navigate([Univers.BOARD.url])
            .then(() => this.toasterService.success('Succès', `L'offre a bien été ajoutée à votre board`));
        },
        () => this.toasterService.error('Oops', `Une erreur est survenue lors de l'ajout de l'offre à votre board`)
      );
  }
}
