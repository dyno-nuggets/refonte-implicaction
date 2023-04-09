import {Injectable} from '@angular/core';
import {ApiHttpService} from '../../../core/services/api-http.service';
import {ApiEndpointsService} from '../../../core/services/api-endpoints.service';
import {Observable} from 'rxjs';
import {User} from '../../../shared/models/user';
import {Pageable} from '../../../shared/models/pageable';
import {HttpClient} from '@angular/common/http';
import {Group} from '../../models/group';

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

  getAll(pageable: Pageable<any>): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllUserEndpoint(pageable));
  }

  getAllPendingActivationUsers(pageable: Pageable<User>): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllPendingActivationUsersEndpoint(pageable));
  }

  enableUser(username: string): Observable<void> {
    return this.http.post<void>(this.apiEndpointsService.getActivateUserEndpoint(username), null);
  }

  getUserGroups(userId: string): Observable<Group[]> {
    return this.http.get<Group[]>(this.apiEndpointsService.getAllGroupsByMemberUsername(userId));
  }

}
