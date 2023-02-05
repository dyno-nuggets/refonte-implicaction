import {Component, Input} from '@angular/core';
import {Topic} from '../../model/topic';
import {Response} from '../../model/response';
import {Univers} from '../../../shared/enums/univers';

@Component({
  selector: 'app-message-block',
  templateUrl: './message-block.component.html',
  styleUrls: ['./message-block.component.scss']
})
export class MessageBlockComponent {

  univers = Univers;
  
  DATETIME_FORMAT = 'dd MMMM yyyy HH:mm';

  @Input()
  message: Topic | Response;

  constructor() {
  }

}
