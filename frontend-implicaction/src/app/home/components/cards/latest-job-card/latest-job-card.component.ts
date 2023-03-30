import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {JobPosting} from '../../../../shared/models/job-posting';
import {Univers} from '../../../../shared/enums/univers';
import {Constants} from '../../../../config/constants';

@Component({
  selector: 'app-latest-job-card',
  templateUrl: './latest-job-card.component.html',
  styleUrls: ['./latest-job-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LatestJobCardComponent {

  @Input()
  job: JobPosting = {};
  univers = Univers;
  constant = Constants;

}
