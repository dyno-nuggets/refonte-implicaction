import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Univers} from "../../../../shared/enums/univers";

@Component({
  selector: 'app-profile-card',
  templateUrl: './profile-card.component.html',
  styleUrls: ['./profile-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileCardComponent {

  readonly univer = Univers;

  @Input() profile;

}
