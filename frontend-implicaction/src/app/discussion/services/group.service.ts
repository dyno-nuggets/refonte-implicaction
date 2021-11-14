import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Group} from '../model/group';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Pageable} from '../../shared/models/pageable';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(
    private http: HttpClient,
    private apiEndpointService: ApiEndpointsService
  ) {
  }

  createGroup(formData: FormData): Observable<Group> {
    return this.http.post<Group>(this.apiEndpointService.createGroupEndpoint(), formData);
  }

  createGroupWithoutPicture(group: any): Observable<Group> {
    return this.http.post<Group>(this.apiEndpointService.createGroupEndpoint(), group);
  }

  findByTopPosting(limit: number): Observable<Group[]> {
    return this.http.get<Group[]>(this.apiEndpointService.findByTopPostingEndpoint(limit));
  }

  getAllGroups(pageable: Pageable): Observable<any> {
    return this.http.get<Group[]>(this.apiEndpointService.findAllGroupsEndpoint(pageable));
  }
}
