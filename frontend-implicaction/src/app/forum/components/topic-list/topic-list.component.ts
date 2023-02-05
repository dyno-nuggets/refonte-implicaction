import {Component, Input} from '@angular/core';
import {Topic} from "../../model/topic";
import {Univers} from "../../../shared/enums/univers";
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.scss']
})
export class TopicListComponent {
  
  constants = Constants;
  univers = Univers;

  @Input()
  topics: Topic[];

  constructor() {
  }

}
