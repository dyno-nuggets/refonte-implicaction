import {Component, Input} from '@angular/core';
import {JobApplication} from '../../models/job-application';
import {Constants} from '../../../config/constants';
import {Univers} from '../../../shared/enums/univers';

@Component({
  selector: 'app-apply-card',
  templateUrl: './apply-card.component.html',
  styleUrls: ['./apply-card.component.scss']
})
export class ApplyCardComponent {

  readonly COMPANY_IMAGE_DEFAULT_URI = Constants.COMPANY_IMAGE_DEFAULT_URI;

  @Input()
  apply: JobApplication;

  univers = Univers;

}
