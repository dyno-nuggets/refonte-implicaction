import {Component, Input} from '@angular/core';
import {Profile} from "../../../../../models/profile/profile";

@Component({
  selector: 'app-profile-details-section',
  templateUrl: './profile-details-section.component.html',
})
export class ProfileDetailsSectionComponent {

  @Input() profile: Profile;

}
