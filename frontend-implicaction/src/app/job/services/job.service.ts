import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Observable} from 'rxjs';
import {JobPosting} from '../../shared/models/job-posting';
import {CriteriaFilter} from '../../shared/models/criteria-filter';
import {Pageable} from '../../shared/models/pageable';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }

  getAllByCriteria(pageable: Pageable, criteria: CriteriaFilter): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllJobEndpoint(pageable, criteria));
  }

  getById(jobId: string): Observable<JobPosting> {
    return this.http.get<JobPosting>(this.apiEndpointsService.getJobByIdEndpoint(jobId));
  }
}
