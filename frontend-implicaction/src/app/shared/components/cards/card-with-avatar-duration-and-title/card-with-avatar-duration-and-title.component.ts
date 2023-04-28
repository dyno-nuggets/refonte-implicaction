import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Profile} from "../../../../community/models/profile";
import {Univers} from "../../../enums/univers";

@Component({
  selector: 'app-card-with-avatar-duration-and-title',
  templateUrl: './card-with-avatar-duration-and-title.component.html',
  styleUrls: ['./card-with-avatar-duration-and-title.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CardWithAvatarDurationAndTitleComponent {

  @Input() profile!: Profile;
  @Input() duration!: string;
  @Input() title!: string;
  @Input() link!: string | string[];

  univers = Univers;
}
