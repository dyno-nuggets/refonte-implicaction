import {Component, Input} from '@angular/core';
import {Post} from '../../model/post';
import {Router} from "@angular/router";



@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.scss'],
})
export class PostCardComponent {
  @Input()
  post: Post;

}
