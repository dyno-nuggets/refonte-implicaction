import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiEndpointsService} from "../../core/services/api-endpoints.service";
import {Pageable} from "../../shared/models/pageable";
import {Topic} from "../model/topic";
import {Response} from "../model/response";
import {CreateTopicPayload} from '../model/createTopicPayload';

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

  createTopic(topic: CreateTopicPayload): Observable<Topic> {
    return this.http.post<Topic>(this.apiEndpointsService.createTopic(), topic);
  }
}
