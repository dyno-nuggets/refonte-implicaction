import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
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
  trackByTopicId = (index: number, topic: Topic) => topic.id;

}
