import {ChangeDetectionStrategy, Component, Input} from '@angular/core';

@Component({
  selector: 'app-counter-card',
  templateUrl: './counter-card.component.html',
  styleUrls: ['./counter-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CounterCardComponent {

  @Input() title: string
  @Input() icon: string;
  @Input() value: number;

}
