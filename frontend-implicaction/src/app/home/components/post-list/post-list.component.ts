import {Component, OnInit} from '@angular/core';
import {ToasterService} from '../../../core/services/toaster.service';
import {Constants} from '../../../config/constants';
import {TopicService} from '../../../forum/services/topic.service';
import {Topic} from '../../../forum/model/topic';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostListComponent implements OnInit {

  latestTopics: Topic[];

  constructor(
    private topicService: TopicService,
    private toasterService: ToasterService
  ) {
  }

  ngOnInit(): void {
    this.topicService
      .getLatest(Constants.LATEST_TOPICS_COUNT)
      .subscribe(
        topics => this.latestTopics = topics,
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données')
      );
  }

  trackByTopicId = (index: number, topic: Topic) => topic.id;

}
