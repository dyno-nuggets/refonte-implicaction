import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiEndpointsService} from "../../core/services/api-endpoints.service";
import {TopicPayload} from "../model/topicPayload";
import {Observable} from "rxjs";
import {Pageable} from "../../shared/models/pageable";
import {Topic} from "../model/topic";
import {Response} from "../model/response";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }

  getTopic(id: number): Observable<Topic> {
    return this.http.get<Topic>(this.apiEndpointsService.getTopic(id));
  }

  getTopicResponses(id: number, pageable: Pageable<Response>): Observable<Pageable<Response>> {
    return this.http.get<Pageable<Response>>(this.apiEndpointsService.getTopicResponses(id, pageable));
  }

  createTopic(topic: TopicPayload): Observable<Topic> {
    return this.http.post<Topic>(this.apiEndpointsService.createTopic(), topic);
  }

  editTopic(topic: TopicPayload): Observable<Topic> {
    return this.http.patch<Topic>(this.apiEndpointsService.editTopic(), topic);
  }

  deleteTopic(topicId: number): Observable<Object> {
    return this.http.delete(this.apiEndpointsService.deleteTopic(topicId));
  }
  
  getLatest(topicCount: number) {
    return this.http.get<Topic[]>(this.apiEndpointsService.getLatestTopics(topicCount));
  }
}
