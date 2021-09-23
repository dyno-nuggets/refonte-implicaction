import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';

@Component({
  selector: 'app-experience-detail',
  templateUrl: './experience-detail.component.html',
  styleUrls: ['./experience-detail.component.scss']
})
export class ExperienceDetailComponent implements OnInit {

  @Input()
  experience: WorkExperience;
  @Input()
  isEditing: boolean;
  yearRange = `1900:${new Date().getFullYear() + 1}`;

  @Output()
  deleteEmitter = new EventEmitter<WorkExperience>();

  constructor() {
  }

  ngOnInit(): void {
  }

  deleteExperience(experience: WorkExperience): void {
    this.deleteEmitter.next(experience);
  }
}
