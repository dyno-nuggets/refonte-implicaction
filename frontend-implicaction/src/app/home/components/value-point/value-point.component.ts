import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {ValuePoint} from "../../models/value-point";

@Component({
  selector: 'app-value-point',
  templateUrl: './value-point.component.html',
  styleUrls: ['./value-point.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ValuePointComponent {
  @Input()
  valuePoint: ValuePoint;
}
