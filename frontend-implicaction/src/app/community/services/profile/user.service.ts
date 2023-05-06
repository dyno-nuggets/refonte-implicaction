import {Injectable} from '@angular/core';
import {ApiHttpService} from '../../../core/services/api-http.service';
import {ApiEndpointsService} from '../../../core/services/api-endpoints.service';
import {Observable} from 'rxjs';
import {User} from '../../../shared/models/user';
import {Pageable} from '../../../shared/models/pageable';
import {HttpClient} from '@angular/common/http';
import {RoleEnumCode} from "../../../shared/enums/role.enum";

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

  getAll(pageable: Pageable<User>): Observable<Pageable<User>> {
    return this.http.get(this.apiEndpointsService.getAllUserEndpoint(pageable));
  }

  enableUser(username: string): Observable<User> {
    return this.http.post<User>(this.apiEndpointsService.getActivateUserEndpoint(username), null);
  }

  updateUserRoles(username: string, roles: RoleEnumCode[]): Observable<User> {
    return this.http.post(this.apiEndpointsService.updateUserRolesEndpoint(username), roles);
  }

  getTotalUsers(): Observable<number> {
    return this.http.get<number>(this.apiEndpointsService.getTotalUsers());
  }
}
