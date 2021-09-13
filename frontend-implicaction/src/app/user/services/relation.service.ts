import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Relation} from '../models/relation';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';

@Injectable({
  providedIn: 'root'
})
export class RelationService {

  constructor(
    private http: HttpClient,
    private apiEndpointService: ApiEndpointsService
  ) {
  }

  getAllByUserId(userId: string): Observable<Relation[]> {
    return this.http.get<Relation[]>(this.apiEndpointService.getAllRelationsByUserIdEndpoint(userId));
  }

  requestFriend(receiverId: string): Observable<Relation> {
    return this.http.post(this.apiEndpointService.createRelationEndpoint(receiverId), null);
  }
}
