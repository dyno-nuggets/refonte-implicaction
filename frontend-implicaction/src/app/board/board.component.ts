import {Component, OnInit} from '@angular/core';
import {ApplyStatusCode, ApplyStatusEnum} from './enums/apply-status-enum';
import {JobApplication} from './models/job-application';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {JobApplicationService} from './services/job-application.service';
import {ToasterService} from '../core/services/toaster.service';
import {BoardContextService} from './services/board-context.service';

export class BoardColumn {
  status: ApplyStatusEnum;
  applies: JobApplication[] = [];
}


@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {


  columns: BoardColumn[] = ApplyStatusEnum.all()
    .map(status => {
      return {status, applies: []};
    });

  constructor(
    private jobBoardService: JobApplicationService,
    private toasterService: ToasterService,
    private boardContextService: BoardContextService
  ) {
  }

  ngOnInit(): void {
    this.jobBoardService
      .getAllForCurrentUser()
      .subscribe(
        applies => applies.forEach(apply =>
          this.columns
            .find(column => column.status.code === apply.statusCode)
            .applies
            .push(apply)),
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la récupération des données.')
      );
    this.boardContextService
      .observe()
      .subscribe(
        applyUpdate => {
          if (!applyUpdate) {
            return;
          }

          const applies = this.columns.find(column => column.applies.find(apply => apply.id === applyUpdate.id))?.applies;
          const applyIndex = applies.findIndex(apply => apply.id === applyUpdate.id);
          if (applyIndex >= 0) {
            // il faut supprimer du board les candidatures acceptées, refusées ou archivées
            if ([ApplyStatusCode.HIRED, ApplyStatusCode.REJECTED].includes(applyUpdate.statusCode) || applyUpdate.archive) {
              applies.splice(applyIndex, 1);
              // sinon, dans le cas d'un changement de statut, on les supprime de l'ancienne colonne pour l'ajouter dans la nouvelle
            } else if (applies[applyIndex].statusCode !== applyUpdate.statusCode) {
              applies.splice(applyIndex, 1);
              const boardColumn = this.columns.find(column => column.status.code === applyUpdate.statusCode);
              boardColumn.applies.push(applyUpdate);
            }
          }
        }
      );
  }

  drop(event: CdkDragDrop<JobApplication[], any>, statusCode: ApplyStatusCode): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
      const jobApply = event.container.data[event.currentIndex];
      this.jobBoardService
        .updateApply({jobId: jobApply.jobId, status: statusCode})
        .subscribe(jobApplyUpdate => jobApply.statusCode = jobApplyUpdate.statusCode);
    }
  }
}
