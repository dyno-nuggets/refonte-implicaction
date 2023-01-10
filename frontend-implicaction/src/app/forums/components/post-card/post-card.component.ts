import {Component, Input} from '@angular/core';
import {Post} from '../../model/post';

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.scss'],
})
export class PostCardComponent {
  @Input()
  post: Post;
}
