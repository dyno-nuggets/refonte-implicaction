import {Component, Input} from '@angular/core';
import {Post} from 'src/app/discussion/model/post';

@Component({
  selector: 'app-latest-posts-list',
  templateUrl: './latest-posts-list.component.html',
})
export class LatestPostsListComponent {

  @Input()
  latestPosts: Post[] = [];
  @Input()
  isLoading = false;

  trackByPostId = (index: number, post: Post) => post.id;

}
