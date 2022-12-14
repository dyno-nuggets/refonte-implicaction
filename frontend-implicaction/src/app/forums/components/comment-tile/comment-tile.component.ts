import {Component, Input} from '@angular/core';
import {Comment} from '../../model/comment';
import {Constants} from '../../../config/constants';
import {Univers} from '../../../shared/enums/univers';

@Component({
  selector: 'app-comment-tile',
  templateUrl: './comment-tile.component.html',
  styleUrls: ['./comment-tile.component.scss']
})
export class CommentTileComponent {

  @Input()
  comment: Comment = {};

  constants = Constants;
  univers = Univers;

}
