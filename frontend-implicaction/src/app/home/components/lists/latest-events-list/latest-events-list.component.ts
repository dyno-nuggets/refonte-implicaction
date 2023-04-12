import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {ImplicactionEvent} from "../../../../shared/models/implicactionEvent";

@Component({
  selector: 'app-events-list',
  templateUrl: './latest-events-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LatestEventsListComponent {

  @Input() latestEvents: ImplicactionEvent[] = []
  @Input() isLoading = false;

  trackById = (index: number, event: ImplicactionEvent) => event.id;

}
