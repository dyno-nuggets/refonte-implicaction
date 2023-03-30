import {Component, Input} from '@angular/core';
import {Post} from 'src/app/discussion/model/post';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
})
export class PostListComponent {

  @Input()
  latestPosts: Post[] = [];
  @Input()
  isLoading = false;

  trackByPostId = (index: number, post: Post) => post.id;

}
