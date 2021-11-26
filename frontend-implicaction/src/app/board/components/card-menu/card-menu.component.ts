import {Component, Input} from '@angular/core';
import {MenuItem} from 'primeng/api';
import {JobApplication} from '../../models/job-application';
import {JobApplicationService} from '../../services/job-application.service';
import {ApplyStatusCode} from '../../enums/apply-status-enum';
import {ToasterService} from '../../../core/services/toaster.service';
import {BoardContextService} from '../../services/board-context.service';

@Component({
  selector: 'app-card-menu',
  templateUrl: './card-menu.component.html',
  styleUrls: ['./card-menu.component.scss']
})
export class CardMenuComponent {

  @Input()
  apply: JobApplication;

  actions: MenuItem[] = [{
    label: 'Options',
    items: [
      {
        label: 'Candidature acceptée',
        icon: 'far fa-check-circle',
        command: () => this.acceptApply()
      },
      {
        label: 'Candidature refusée',
        icon: 'far fa-times-circle',
        command: () => this.declineApply()
      },
      {
        label: 'Annuler la candidature',
        icon: 'far fa-trash-alt pe-1',
        command: () => this.cancelApply()
      },
      {
        label: 'Archiver la candidature',
        icon: 'far fa-folder-open',
        command: () => this.archiveApply()
      }]
  }];

  constructor(
    private applicationService: JobApplicationService,
    private toasterService: ToasterService,
    private boardContextService: BoardContextService
  ) {
  }

  acceptApply(): void {
    this.updateApply(ApplyStatusCode.HIRED, true, 'Votre candidature est désormais acceptée.');
  }

  declineApply(): void {
    this.updateApply(ApplyStatusCode.REJECTED, true, 'Votre candidature est désormais refusée.');
  }

  archiveApply(): void {
    this.updateApply(this.apply.statusCode, true, 'Votre candidature est désormais archivée.');
  }

  cancelApply(): void {
    this.applicationService
      .deleteApply(this.apply.jobId)
      .subscribe(
        () => this.apply.statusCode = ApplyStatusCode.CANCELED,
        () => this.toasterService.error('Oops', `Une erreur est survenue lors de l'annulation de la candidature.`),
        () => {
          this.boardContextService.apply = this.apply;
          this.toasterService.success('Succès', 'Votre candidature a bien été annulée.');
        }
      );
  }

  private updateApply(statusCode: ApplyStatusCode, archive: boolean, successMessage: string): void {
    this.applicationService
      .updateApply({jobId: this.apply.jobId, status: statusCode, archive})
      .subscribe(
        (applyUpdate) => {
          this.apply.statusCode = applyUpdate.statusCode;
          this.apply.archive = applyUpdate.archive;
        },
        () => this.toasterService.error('Oops', `Une erreur est survenue lors de la mise à jour d'une candidature.`),
        () => {
          this.toasterService.success('Succès', successMessage);
          this.boardContextService.apply = this.apply;
        }
      );
  }
}
