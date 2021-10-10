import {Component, Input, OnInit} from '@angular/core';
import {JobPosting} from '../../../shared/models/job-posting';
import {Univers} from '../../../shared/enums/univers';

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.scss']
})
export class JobCardComponent implements OnInit {

  @Input()
  job: JobPosting;
  univers = Univers;

  date = new Date(2021, 9, 10, 3, 31);

  constructor() {
  }

  ngOnInit(): void {
  }
}
