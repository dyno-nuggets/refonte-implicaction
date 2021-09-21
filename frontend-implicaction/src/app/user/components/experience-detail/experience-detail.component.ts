import {Component, Input, OnInit} from '@angular/core';
import {WorkExperience} from '../../../shared/models/work-experience';
import {UserService} from '../../services/user.service';

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

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
  }

  // TODO delete experience
  // TODO add experience
}
