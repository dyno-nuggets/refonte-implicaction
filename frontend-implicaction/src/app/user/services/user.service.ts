import {Injectable} from '@angular/core';
import {ApiHttpService} from '../../core/services/api-http.service';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Observable} from 'rxjs';
import {User} from '../../shared/models/user';
import {Pageable} from '../../shared/models/pageable';
import {HttpClient} from '@angular/common/http';
import {Relation} from '../models/relation';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private apiHttpService: ApiHttpService,
    private apiEndpointsService: ApiEndpointsService
  ) {

  }

  getAll(pageable: Pageable): Observable<any> {
    return (this.apiHttpService
      .get(this.apiEndpointsService.getAllUserEndpoint(pageable)) as Observable<Pageable<User>>);
  }

  getUserById(userId: string): Observable<User> {
    return this.http.get(this.apiEndpointsService.getUserByIdEndpoint(userId));
  }

  getUserFriends(userId: string, pageable: Pageable): Observable<any> {
    return this.http.get<Pageable<User>>(this.apiEndpointsService.getAllFriendsByUserIdEndPoint(userId, pageable));
  }

  getUserFriendRequestReceived(pageable: Pageable): Observable<any> {
    return this.http.get<Pageable<User>>(this.apiEndpointsService.getFriendRequestReceivedEndpoint(pageable));
  }

  getUserFriendRequestSent(pageable: Pageable): Observable<any> {
    return this.http.get<Pageable<User>>(this.apiEndpointsService.getFriendRequestSentEndPoint(pageable));
  }

  confirmUserAsFriend(senderId: string): Observable<Relation> {
    return this.http.get(this.apiEndpointsService.confirmUserAsFriendEndpoint(senderId));
  }

  removeUserFromFriends(userId: string): Observable<any> {
    return this.http.delete(this.apiEndpointsService.cancelRelationByUserEndpoint(userId));
  }

  updatePersonalInfo(userId: string, personalInfos: User): Observable<User> {
    return this.http.put<User>(this.apiEndpointsService.updatePersonalInfoByUserIdEndpoint(userId), personalInfos);
  }

  getAllPendingActivationUsers(pageable: Pageable): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllPendingActivationUsersEndpoint(pageable));
  }
}
