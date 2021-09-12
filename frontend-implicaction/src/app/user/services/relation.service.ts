import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Relation} from '../models/relation';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Pageable} from '../../shared/models/pageable';
import {User} from '../../shared/models/user';

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

  getFriends(userId: string, pageable: Pageable): Observable<Pageable<User>> {
    return this.http.get<Pageable<User>>(this.apiEndpointService.getAllFriendsByUserIdEndPoint(userId, pageable));
  }

  getFriendsRequests(type: string, pageable: Pageable): Observable<Pageable<User>> {
    return this.http.get<Pageable<User>>(this.apiEndpointService.getAllFriendsByTypeEndPoint(type, pageable));
  }

  requestFriend(receiverId: string): Observable<Relation> {
    return this.http.post(this.apiEndpointService.createRelationEndpoint(receiverId), null);
  }
}
