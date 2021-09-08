import {Component, Input, OnInit} from '@angular/core';
import {WorkExperience} from "../../../../shared/models/work-experience";

@Component({
  selector: 'app-work-experience-detail',
  templateUrl: './work-experience-detail.component.html',
  styleUrls: ['./work-experience-detail.component.scss']
})
export class WorkExperienceDetailComponent implements OnInit {

  @Input()
  workExperience: WorkExperience;

  constructor() { }

  ngOnInit(): void {
  }

}
