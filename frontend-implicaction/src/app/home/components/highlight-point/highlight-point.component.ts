import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {HighlightPoint} from "../../models/highlight-point";

@Component({
  selector: 'app-highlight-point',
  templateUrl: './highlight-point.component.html',
  styleUrls: ['./highlight-point.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HighlightPointComponent {
  @Input()
  valuePoint: HighlightPoint;
}
