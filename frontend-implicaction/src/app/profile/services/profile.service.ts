import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {ProfileUpdateRequest} from "../models/profile-update-request";
import {HttpClient} from "@angular/common/http";
import {ApiHttpService} from "../../core/services/api-http.service";
import {ApiEndpointsService} from "../../core/services/api-endpoints.service";
import {Profile} from "../models/profile";
import {Pageable} from "../../shared/models/pageable";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(
    private http: HttpClient,
    private apiHttpService: ApiHttpService,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }


  getProfileByUsername(username: string): Observable<Profile> {
    return this.http.get<Profile>(this.apiEndpointsService.getProfileByUsernameEndpoint(username));
  }

  updateProfile(updateRequest: ProfileUpdateRequest): Observable<Profile> {
    return this.http.put<Profile>(this.apiEndpointsService.updateProfileEndpoint(), updateRequest);
  }

  getAllProfiles(pageable: Pageable<any>): Observable<Pageable<Profile>> {
    return this.http.get<Pageable<Profile>>(this.apiEndpointsService.getAllProfilesEndpoint(pageable));
  }
}
