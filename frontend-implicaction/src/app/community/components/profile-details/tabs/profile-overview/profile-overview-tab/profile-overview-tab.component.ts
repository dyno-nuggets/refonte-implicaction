import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Profile} from "../../../../../models/profile/profile";

@Component({
  selector: 'app-profile-overview-tab',
  templateUrl: './profile-overview-tab.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileOverviewTabComponent {

  @Input() profile: Profile;

}
