import {Component, Input, OnInit} from '@angular/core';
import {JobPosting} from '../../../shared/models/job-posting';

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.scss']
})
export class JobCardComponent implements OnInit {

  @Input()
  job: JobPosting;

  constructor() {
  }

  ngOnInit(): void {
  }

}
