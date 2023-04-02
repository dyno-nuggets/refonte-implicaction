import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Alert} from '../../models/alert';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AlertComponent {

  @Input()
  alert?: Alert;

}
