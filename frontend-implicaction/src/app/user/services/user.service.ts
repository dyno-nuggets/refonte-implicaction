import {Injectable} from '@angular/core';
import {ApiHttpService} from '../../core/services/api-http.service';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Observable} from 'rxjs';
import {User} from '../../shared/models/user';
import {Pageable} from '../../shared/models/pageable';
import {HttpClient} from '@angular/common/http';

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

  getAll(pageable: Pageable): Observable<Pageable<User>> {
    return (this.apiHttpService
      .get(this.apiEndpointsService.getAllUserEndpoint(pageable)) as Observable<Pageable<User>>);
  }

  getUserById(userId: string): Observable<User> {
    return this.http.get(this.apiEndpointsService.getUserByIdEndpoint(userId));
  }

  getUserFriends(userId: string, pageable: Pageable): Observable<Pageable<User>> {
    return this.http.get<Pageable<User>>(this.apiEndpointsService.getAllFriendsByUserIdEndPoint(userId, pageable));
  }

  getUserFriendRequestReceived(pageable: Pageable): Observable<Pageable<User>> {
    return this.http.get<Pageable<User>>(this.apiEndpointsService.getFriendRequestReceived(pageable));
  }

  getUserFriendRequestSent(pageable: Pageable): Observable<Pageable<User>> {
    return this.http.get<Pageable<User>>(this.apiEndpointsService.getFriendRequestSent(pageable));
  }
}
