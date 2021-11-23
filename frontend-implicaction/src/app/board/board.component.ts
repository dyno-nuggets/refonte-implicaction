import {Component, OnInit} from '@angular/core';
import {ApplyStatusCode, ApplyStatusEnum} from './enums/apply-status-enum';
import {JobApplication} from './models/job-application';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {JobBoardService} from './services/job-board.service';
import {ToasterService} from '../core/services/toaster.service';

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
    private jobBoardService: JobBoardService,
    private toasterService: ToasterService
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
      jobApply.statusCode = statusCode;
    }
  }
}
