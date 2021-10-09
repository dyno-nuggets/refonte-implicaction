import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Pageable} from '../../shared/models/pageable';
import {Observable} from 'rxjs';
import {JobPosting} from '../../shared/models/job-posting';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }

  getAll(pageable: Pageable, searchKey: string): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllJobEndpoint(pageable, searchKey));
  }

  getById(jobId: string): Observable<JobPosting> {
    return (this.http.get<JobPosting>(this.apiEndpointsService.getJobByIdEndpoint(jobId)));
  }
}
