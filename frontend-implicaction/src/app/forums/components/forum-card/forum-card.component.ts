import {Component, Input} from '@angular/core';
import {Group} from '../../model/group';

@Component({
  selector: 'app-forum-card',
  templateUrl: './forum-card.component.html',
  styleUrls: ['./forum-card.component.scss'],
})
export class ForumCardComponent {
  @Input()
  group: Group;
}
