import { Component, Input, OnInit } from '@angular/core';
import { Post } from '../../model/post';
import { Univers } from '../../../shared/enums/univers';
import { Constants } from '../../../config/constants';

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.scss'],
})
export class PostTileComponent implements OnInit {
  @Input()
  post: Post = {};
  univers = Univers;
  constant = Constants;

  updateCommentCount(count: number): void {
    this.post.commentCount = count;
  }
  ngOnInit(): void {
    console.log(this.post);
  }
}
