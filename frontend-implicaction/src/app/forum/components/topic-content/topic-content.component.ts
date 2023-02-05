import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";

import {
  BaseWithPaginationAndFilterComponent
} from "../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component";
import {Response} from "../../model/response";
import {Criteria} from "../../../shared/models/Criteria";
import {TopicService} from "../../services/topic.service";
import {Topic} from "../../model/topic";
import {map, switchMap} from "rxjs/operators";
import {Pageable} from "../../../shared/models/pageable";

@Component({
  selector: 'app-topic-content',
  templateUrl: './topic-content.component.html',
  styleUrls: ['./topic-content.component.scss']
})
export class TopicContentComponent extends BaseWithPaginationAndFilterComponent<Response, Criteria> implements OnInit {
  topic$: Observable<Topic>;
  paginatedResponses$: Observable<Pageable<Response>>;
  private $id: Observable<number>;

  constructor(private currentRoute: ActivatedRoute, private topicService: TopicService) {
    super(currentRoute);
  }

  ngOnInit(): void {
    this.pageable = {...this.pageable, sortBy: 'createdAt', sortOrder: 'ASC'};
    this.$id = this.currentRoute.params.pipe(map(map => +map['id']));
    this.topic$ = this.$id.pipe(switchMap(id => this.topicService.getTopic(id)));
    this.paginate();
  }

  protected innerPaginate() {
    this.paginatedResponses$ = this.$id.pipe(switchMap(id => this.topicService.getTopicResponses(id, this.pageable)));
    this.paginatedResponses$.subscribe((paginatedResponses) => {
      this.pageable.totalPages = paginatedResponses.totalPages;
      this.pageable.totalElements = paginatedResponses.totalElements;
      this.pageable.content = paginatedResponses.content;
    });
  }

}
