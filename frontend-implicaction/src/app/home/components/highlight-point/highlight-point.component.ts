import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {ValuePoint} from "../../models/value-point";

@Component({
  selector: 'app-highlight-point',
  templateUrl: './highlight-point.component.html',
  styleUrls: ['./highlight-point.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HighlightPointComponent {
  @Input()
  valuePoint: ValuePoint;
}
