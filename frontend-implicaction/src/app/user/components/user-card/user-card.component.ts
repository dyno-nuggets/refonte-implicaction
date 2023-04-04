import {Component, Input, TemplateRef} from '@angular/core';
import {Constants} from '../../../config/constants';
import {Univers} from '../../../shared/enums/univers';
import {Profile} from "../../../profile/models/profile";

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.scss']
})
export class UserCardComponent {

  readonly constant = Constants;
  readonly univer = Univers;

  @Input()
  profile: Profile;

  @Input()
  innerTemplate: TemplateRef<any>;


}
