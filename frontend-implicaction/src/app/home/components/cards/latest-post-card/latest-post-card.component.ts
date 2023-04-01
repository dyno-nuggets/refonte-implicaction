import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Univers} from '../../../../shared/enums/univers';
import {Constants} from '../../../../config/constants';
import {Topic} from "../../../../forum/model/topic";


@Component({
  selector: 'app-latest-post-card',
  templateUrl: './latest-post-card.component.html',
  styleUrls: ['./latest-post-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LatestPostCardComponent {

  @Input()
  topic: Topic;
  univers = Univers;
  constant = Constants;
}
