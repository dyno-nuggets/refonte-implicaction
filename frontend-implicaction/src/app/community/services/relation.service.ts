import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Relation} from '../models/relation';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Pageable} from "../../shared/models/pageable";
import {User} from "../../shared/models/user";

@Injectable({
  providedIn: 'root'
})
export class RelationService {

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }

  getAllRelationsByUserId(userId: string, pageable: Pageable<any>): Observable<any> {
    return this.http.get<Pageable<User>>(this.apiEndpointsService.getAllRelationsByUserIdEndPoint(userId, pageable));
  }

  getAllRelationRequestsReceived(pageable: Pageable<any>): Observable<any> {
    return this.http.get<Pageable<User>>(this.apiEndpointsService.getAllRelationRequestsReceivedEndpoint(pageable));
  }

  getAllRelationRequestSent(pageable: Pageable<any>): Observable<any> {
    return this.http.get<Pageable<User>>(this.apiEndpointsService.getAllRelationRequestSentEndPoint(pageable));
  }

  requestFriend(receiverId: string): Observable<Relation> {
    return this.http.post(this.apiEndpointsService.createRelationEndpoint(receiverId), null);
  }
}
