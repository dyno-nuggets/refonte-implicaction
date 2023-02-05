import {Component, Input} from '@angular/core';
import {Topic} from "../../model/topic";
import {Univers} from "../../../shared/enums/univers";

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.scss']
})
export class TopicListComponent {

  DATE_FORMAT = 'dd MMMM yyyy';
  DATETIME_FORMAT = this.DATE_FORMAT + ' HH:mm';

  univers = Univers;

  @Input()
  topics: Topic[];

  constructor() {
  }

}
