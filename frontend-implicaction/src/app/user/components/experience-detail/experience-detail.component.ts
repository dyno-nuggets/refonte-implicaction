import {Component, Input, OnInit} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';

@Component({
  selector: 'app-experience-detail',
  templateUrl: './experience-detail.component.html',
  styleUrls: ['./experience-detail.component.scss']
})
export class ExperienceDetailComponent implements OnInit {

  @Input()
  experience: WorkExperience;

  constructor() {
  }

  ngOnInit(): void {
  }

}
