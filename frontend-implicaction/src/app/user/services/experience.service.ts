import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {WorkExperience} from '../../shared/models/work-experience';

@Injectable({
  providedIn: 'root'
})
export class ExperienceService {

  constructor(
    private http: HttpClient,
    private apiEndpointService: ApiEndpointsService
  ) {
  }

  updateExperience(userId: string, experience: WorkExperience): Observable<WorkExperience> {
    return this.http.put<WorkExperience>(this.apiEndpointService.updateExperienceEndpoint(), experience);
  }

  createExperience(userId: string, experience: WorkExperience): Observable<WorkExperience> {
    return this.http.post<WorkExperience>(this.apiEndpointService.createExperienceEndpoint(), experience);
  }

  deleteExperience(experienceId: string): Observable<any> {
    return this.http.delete(this.apiEndpointService.deleteExperienceEndpoint(experienceId));
  }
}
