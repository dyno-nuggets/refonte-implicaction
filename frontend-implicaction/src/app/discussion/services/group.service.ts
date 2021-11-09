import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Group} from '../model/group';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(
    private http: HttpClient,
    private apiEndpointService: ApiEndpointsService
  ) {
  }

  createGroup(group: Group): Observable<Group> {
    return this.http.post<Group>(this.apiEndpointService.createGroupEndpoint(), group);
  }

  findByTopPosting(limit: number): Observable<Group[]> {
    return this.http.get<Group[]>(this.apiEndpointService.findByTopPostingEndpoint(limit));
  }
}
