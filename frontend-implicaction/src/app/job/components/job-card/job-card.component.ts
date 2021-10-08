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

  constructor() {
  }

  ngOnInit(): void {
  }

}
