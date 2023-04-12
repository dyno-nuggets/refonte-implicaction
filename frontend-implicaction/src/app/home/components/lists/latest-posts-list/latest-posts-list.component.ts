import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Topic} from "../../../../forum/model/topic";
import {Univers} from "../../../../shared/enums/univers";

@Component({
  selector: 'app-latest-posts-list',
  templateUrl: './latest-posts-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LatestPostsListComponent {

  @Input() latestTopics: Topic[] = [];
  @Input() isLoading = false;
  @Input() size = 3;

  univers = Univers;
  trackByTopicId = (index: number, topic: Topic) => topic.id;

}
