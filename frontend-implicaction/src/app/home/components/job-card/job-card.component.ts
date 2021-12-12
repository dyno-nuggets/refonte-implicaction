import {Component, Input} from '@angular/core';
import {JobPosting} from '../../../shared/models/job-posting';
import {Univers} from '../../../shared/enums/univers';
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.scss']
})
export class JobCardComponent {

  @Input()
  job: JobPosting = {};
  univers = Univers;
  constant = Constants;

}
