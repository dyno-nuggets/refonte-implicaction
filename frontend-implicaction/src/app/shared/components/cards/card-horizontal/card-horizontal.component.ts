import {ChangeDetectionStrategy, Component, Input} from '@angular/core';

@Component({
  selector: 'app-card-horizontal',
  templateUrl: './card-horizontal.component.html',
  styleUrls: ['./card-horizontal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CardHorizontalComponent {

  @Input() link: string | string[];

}
