import {Component, Input, OnInit} from '@angular/core';
import {JobApplication} from '../../models/job-application';
import {Constants} from '../../../config/constants';
import {Univers} from '../../../shared/enums/univers';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-apply-card',
  templateUrl: './apply-card.component.html',
  styleUrls: ['./apply-card.component.scss']
})
export class ApplyCardComponent implements OnInit {

  readonly COMPANY_IMAGE_DEFAULT_URI = Constants.COMPANY_IMAGE_DEFAULT_URI;

  @Input()
  apply: JobApplication;

  univers = Univers;
  actions: MenuItem[];

  ngOnInit(): void {
  }


}
