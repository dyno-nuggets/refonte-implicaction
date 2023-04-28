import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {RoleEnumCode} from "../../../shared/enums/role.enum";
import {Univers} from "../../../shared/enums/univers";
import {Profile} from "../../../community/models/profile";

@Component({
  selector: 'app-navbar-profile-dropdown',
  templateUrl: './navbar-profile-dropdown.component.html',
  styleUrls: ['./navbar-profile-dropdown.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavbarProfileDropdownComponent {

  @Input() currentProfile: Profile;

  roleAdminCode = RoleEnumCode.ADMIN;
  univers = Univers;
}
