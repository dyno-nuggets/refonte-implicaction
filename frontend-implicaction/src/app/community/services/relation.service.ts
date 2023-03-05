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

  getAllRelationRequestsReceived(username: string, pageable: Pageable<Relation>): Observable<Pageable<Relation>> {
    return this.http.get<Pageable<Relation>>(this.apiEndpointsService.getAllRelationRequestsReceivedEndpoint(username, pageable));
  }

  getAllRelationRequestSent(username: string, pageable: Pageable<Relation>): Observable<Pageable<Relation>> {
    return this.http.get<Pageable<Relation>>(this.apiEndpointsService.getAllRelationRequestSentEndPoint(username, pageable));
  }

  requestRelation(receiverName: string): Observable<Relation> {
    return this.http.post(this.apiEndpointsService.createRelationEndpoint(receiverName), null);
  }

  getAllCommunity(pageable: Pageable<Relation>): Observable<Pageable<Relation>> {
    return this.http.get<Pageable<Relation>>(this.apiEndpointsService.getAllCommunity(pageable));
  }

  confirmRelation(relationId: string): Observable<Relation> {
    return this.http.post<Relation>(this.apiEndpointsService.confirmRelationEndpoint(relationId), null);
  }

  removeRelation(relationId: string): Observable<void> {
    return this.http.delete<void>(this.apiEndpointsService.cancelRelationEndpoint(relationId));
  }
}
