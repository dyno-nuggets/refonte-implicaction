import {Component, Input} from '@angular/core';
import {Constants} from "../../../config/constants";
import {Profile} from "../../../profile/models/profile";

@Component({
  selector: 'app-user-avatar',
  templateUrl: './user-avatar.component.html',
  styleUrls: ['./user-avatar.component.scss']
})
export class UserAvatarComponent {

  @Input() profile: Profile;

  @Input() shape: 'rounded' | 'rounded-circle' = 'rounded';

  @Input() size: 'xs' | 'small' | 'medium' | 'large' | 'xlarge' | 'xxlarge' = 'medium';

  @Input() shadow = false;

  @Input() imageClass;

  constants = Constants;
}
