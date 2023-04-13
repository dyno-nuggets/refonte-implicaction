import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Topic} from "../../../../forum/model/topic";
import {Univers} from "../../../../shared/enums/univers";
import {Constants} from "../../../../config/constants";

@Component({
  selector: 'app-latest-posts-list',
  templateUrl: './latest-posts-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LatestPostsListComponent {

  @Input() latestTopics: Topic[] = [];
  @Input() isLoading = false;
  @Input() size = Constants.LATEST_TOPICS_COUNT;

  univers = Univers;
  trackByTopicId = (index: number, topic: Topic) => topic.id;

}
