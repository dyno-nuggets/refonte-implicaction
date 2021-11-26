import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {JobApplication} from '../models/job-application';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {JobApplicationRequest} from '../models/job-application-request';

@Injectable({
  providedIn: 'root'
})
export class JobApplicationService {

  constructor(
    private http: HttpClient,
    private apiEndpointService: ApiEndpointsService
  ) {
  }

  createApplication(request: JobApplicationRequest): Observable<JobApplication> {
    return this.http.post<JobApplication>(this.apiEndpointService.createJobApplicationEndpoint(), request);
  }

  getAllForCurrentUser(): Observable<JobApplication[]> {
    return this.http.get<JobApplication[]>(this.apiEndpointService.getAllApplicationForCurrentUserEndpoint());
  }

  updateApply(jobApplicationRequest: JobApplicationRequest): Observable<JobApplication> {
    return this.http.patch<JobApplication>(this.apiEndpointService.updateApplicationStatusEndpoint(), jobApplicationRequest);
  }

  deleteApply(jobId: string): Observable<any> {
    return this.http.delete(this.apiEndpointService.deleteApplicationEndpoint(jobId));
  }
}
