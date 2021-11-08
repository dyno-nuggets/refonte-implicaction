import {Component, Input} from '@angular/core';
import {Post} from '../../model/post';
import {Univers} from '../../../shared/enums/univers';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.scss']
})
export class PostDetailsComponent {

  @Input()
  post: Post = {};

  univers = Univers;
}
