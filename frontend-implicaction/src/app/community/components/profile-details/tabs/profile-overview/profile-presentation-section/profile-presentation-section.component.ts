import {Component, Input} from '@angular/core';
import {Profile} from "../../../../../models/profile/profile";

@Component({
  selector: 'app-profile-presentation-section',
  templateUrl: './profile-presentation-section.component.html',
})
export class ProfilePresentationSectionComponent {

  @Input() profile!: Profile;

}
