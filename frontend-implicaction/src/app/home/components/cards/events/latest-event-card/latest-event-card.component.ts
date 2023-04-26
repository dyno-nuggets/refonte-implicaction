import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {ImplicactionEvent} from '../../../../../shared/models/implicaction-event';

@Component({
  selector: 'app-latest-event-card',
  templateUrl: './latest-event-card.component.html',
  styleUrls: ['./latest-event-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LatestEventCardComponent {

  @Input() event: ImplicactionEvent;
  @Input() link = '/';

}
