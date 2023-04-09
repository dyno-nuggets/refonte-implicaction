import {Component, Input} from '@angular/core';
import {Profile} from "../../../profile/models/profile";
import {Univers} from "../../enums/univers";

@Component({
  selector: 'app-profile-info-display',
  templateUrl: './profile-info-display.component.html',
  styleUrls: ['./profile-info-display.component.scss']
})
export class ProfileInfoDisplayComponent {
  @Input()
  profile?: Profile;

  univer = Univers;

}
