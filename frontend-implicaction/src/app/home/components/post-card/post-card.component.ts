import {Component, Input} from '@angular/core';
import {Univers} from '../../../shared/enums/univers';
import {Constants} from '../../../config/constants';
import {Topic} from '../../../forum/model/topic';


@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.scss']
})
export class PostCardComponent {

  @Input()
  topic: Topic;
  univers = Univers;
  constants = Constants;
}
