import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Profile} from "../../../models/profile";

@Component({
  selector: 'app-edit-profile-tab',
  templateUrl: './edit-profile-tab.component.html',
  styleUrls: ['./edit-profile-tab.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EditProfileTabComponent {

  @Input() profile!: Profile;

}
