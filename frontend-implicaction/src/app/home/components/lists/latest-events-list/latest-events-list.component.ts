import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {ImplicactionEvent} from '../../../../shared/models/implicaction-event';
import {Constants} from '../../../../config/constants';

@Component({
  selector: 'app-events-list',
  templateUrl: './latest-events-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LatestEventsListComponent {

  @Input() latestEvents: ImplicactionEvent[] = [];
  @Input() isLoading = false;
  @Input() size = Constants.LATEST_EVENTS_COUNT;

  trackById = (index: number, event: ImplicactionEvent) => event.id;

}
