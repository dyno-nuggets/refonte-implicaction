import {Component, Input} from '@angular/core';
import {Profile} from "../../../../models/profile/profile";

@Component({
  selector: 'app-edit-profile-tab',
  templateUrl: './edit-profile-tab.component.html',
  styleUrls: ['./edit-profile-tab.component.scss']
})
export class EditProfileTabComponent {

  @Input() profile!: Profile;

}
