import {Component, OnInit} from '@angular/core';
import {ApplyStatusCode, ApplyStatusEnum} from './enums/apply-status-enum';
import {JobApply} from './models/job-apply';

export class BoardColumn {
  status: ApplyStatusEnum;
  applies: JobApply[] = [];
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

  ngOnInit(): void {
    this.columns
      .find(column => column.status.code === ApplyStatusCode.PENDING)
      .applies
      .push({
        jobTitle: 'Responsable de la maintenance et de la sécurité',
        contractType: 'CDI',
        companyImageUrl: 'https://www.netanswer.fr/wp-content/uploads/2017/01/logoNAli400.png',
        companyName: 'Net Answer',
        location: 'France, Paris(75)',
        jobId: '12',
        status: ApplyStatusEnum.PENDING
      });
  }
}
