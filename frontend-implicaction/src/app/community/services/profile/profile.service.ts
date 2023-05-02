import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ProfileUpdateRequest} from '../../models/profile-update-request';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {ApiEndpointsService} from '../../../core/services/api-endpoints.service';
import {Profile} from '../../models/profile';
import {Pageable} from '../../../shared/models/pageable';
import {RelationCriteriaEnum} from '../../models/enums/relation-criteria-enum';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }


  getProfileByUsername(username: string): Observable<Profile> {
    return this.http.get<Profile>(this.apiEndpointsService.getProfileByUsernameEndpoint(username));
  }

  updateProfile(updateRequest: ProfileUpdateRequest): Observable<Profile> {
    return this.http.put<Profile>(this.apiEndpointsService.updateProfileEndpoint(), updateRequest);
  }

  uploadProfileAvatar(file: File, username: string): Observable<HttpEvent<Profile>> {
    const formData: FormData = new FormData();
    formData.set('file', file);
    const req = new HttpRequest('POST', this.apiEndpointsService.updateImageProfileEndpoint(username), formData, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request<Profile>(req);
  }

  getAllProfiles(relationCriteria: RelationCriteriaEnum, pageable: Pageable<Profile>): Observable<Pageable<Profile>> {
    return this.http.get<Pageable<Profile>>(this.apiEndpointsService.getAllProfilesEndpoint(relationCriteria, pageable));
  }
}
