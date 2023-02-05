import {Component, Input} from '@angular/core';
import {Topic} from '../../model/topic';
import {Response} from '../../model/response';
import {Univers} from '../../../shared/enums/univers';
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-message-block',
  templateUrl: './message-block.component.html',
  styleUrls: ['./message-block.component.scss']
})
export class MessageBlockComponent {

  univers = Univers;

  constants = Constants;

  @Input()
  message: Topic | Response;

  constructor() {
  }

}
