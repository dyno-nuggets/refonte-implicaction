import {ChangeDetectionStrategy, Component, Input} from '@angular/core';

@Component({
  selector: 'app-contract-type',
  templateUrl: './contract-type.component.html',
  styleUrls: ['./contract-type.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ContractTypeComponent {

  @Input()
  type: string;
}
