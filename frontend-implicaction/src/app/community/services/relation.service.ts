import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Relation} from '../models/relation';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Pageable} from "../../shared/models/pageable";

@Injectable({
  providedIn: 'root'
})
export class RelationService {

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }

  getAllRelationsByUsername(username: string, pageable: Pageable<Relation>): Observable<Pageable<Relation>> {
    return this.http.get<Pageable<Relation>>(this.apiEndpointsService.getAllRelationsByUsernameEndPoint(username, pageable));
  }

  getAllRelationRequestsReceived(pageable: Pageable<Relation>): Observable<Pageable<Relation>> {
    return this.http.get<Pageable<Relation>>(this.apiEndpointsService.getAllRelationRequestsReceivedEndpoint(pageable));
  }

  getAllRelationRequestSent(pageable: Pageable<Relation>): Observable<Pageable<Relation>> {
    return this.http.get<Pageable<Relation>>(this.apiEndpointsService.getAllRelationRequestSentEndPoint(pageable));
  }

  requestFriend(receiverId: string): Observable<Relation> {
    return this.http.post(this.apiEndpointsService.createRelationEndpoint(receiverId), null);
  }

  getAllCommunity(pageable: Pageable<Relation>): Observable<Pageable<Relation>> {
    return this.http.get<Pageable<Relation>>(this.apiEndpointsService.getAllCommunity(pageable));
  }
}
