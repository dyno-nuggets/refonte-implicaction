import {Component, Input} from '@angular/core';
import {Topic} from "../../model/topic";

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.scss']
})
export class TopicListComponent {

  @Input()
  topics: Topic[];

  constructor() {
  }

}
