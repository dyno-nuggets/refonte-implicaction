import {Component} from '@angular/core';
import {JobPosting} from '../shared/models/job-posting';
import {JobStatus} from './enums/job-status';

export class BoardColumn {
  status: JobStatus;
  jobs: JobPosting[] = [];
}


@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent {

  columns: BoardColumn[] = JobStatus.all()
    .map(status => {
      return {status, jobs: []};
    });
}
