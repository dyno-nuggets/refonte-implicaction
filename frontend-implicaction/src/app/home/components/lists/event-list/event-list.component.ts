import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {ImplicactionEvent} from "../../../../shared/models/implicactionEvent";

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EventListComponent {

  @Input()
  latestEvents: ImplicactionEvent[] = []
  @Input()
  isLoading = false;

  trackById = (index: number, event: ImplicactionEvent) => event.id;

}
