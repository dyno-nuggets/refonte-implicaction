import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {ProfileUpdateRequest} from "../../models/profile/profile-update-request";
import {HttpClient} from "@angular/common/http";
import {ApiEndpointsService} from "../../../core/services/api-endpoints.service";
import {Profile} from "../../models/profile/profile";

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
}
