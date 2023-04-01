import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Post} from 'src/app/discussion/model/post';
import {Topic} from "../../../../forum/model/topic";

@Component({
  selector: 'app-latest-posts-list',
  templateUrl: './latest-posts-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LatestPostsListComponent {

  @Input()
  latestTopics: Topic[] = [];
  @Input()
  isLoading = false;
  trackByPostId = (index: number, post: Post) => post.id;

}
