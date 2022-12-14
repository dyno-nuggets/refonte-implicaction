import {Component, Input} from '@angular/core';
import {Post} from '../../../forums/model/post';
import {Univers} from '../../../shared/enums/univers';
import {Constants} from '../../../config/constants';


@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.scss']
})
export class PostCardComponent {

  @Input()
  post: Post = {};
  univers = Univers;
  constant = Constants;
}
