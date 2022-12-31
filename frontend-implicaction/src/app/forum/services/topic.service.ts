import {Injectable} from '@angular/core';
import {Topic} from "../model/topic";
import {HttpClient} from "@angular/common/http";
import {ApiHttpService} from "../../core/services/api-http.service";
import {ApiEndpointsService} from "../../core/services/api-endpoints.service";
import {CreateTopicPayload} from "../model/createTopicPayload";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient,
              private apiHttpService: ApiHttpService,
              private apiEndpointsService: ApiEndpointsService) {
  }

  createTopic(topic: CreateTopicPayload): Observable<Topic> {
    return this.http.post<Topic>(this.apiEndpointsService.createTopic(), topic);
  }
}
