import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Observable} from 'rxjs';
import {JobPosting} from '../../shared/models/job-posting';
import {JobCriteriaFilter} from '../models/job-criteria-filter';
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

  getAllByCriteria(pageable: Pageable, criteria: JobCriteriaFilter, archive = null, applyCheck = false): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllJobEndpoint(pageable, criteria, archive, applyCheck));
  }

  getAllValidatedByCriteria(pageable: Pageable, criteria: JobCriteriaFilter, archive = null): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllValidatedJobEndpoint(pageable, criteria, archive));
  }

  getById(jobId: string): Observable<JobPosting> {
    return this.http.get<JobPosting>(this.apiEndpointsService.getJobByIdEndpoint(jobId));
  }

  getLatestJobs(jobsCount: number): Observable<JobPosting[]> {
    return this.http.get<JobPosting[]>(this.apiEndpointsService.getLatestJobsEndpoint(jobsCount));
  }

  createJob(job: JobPosting): Observable<JobPosting> {
    return this.http.post<JobPosting>(this.apiEndpointsService.createJobPostingEndpoint(), job);
  }

  updateJob(jobPosting: JobPosting): Observable<JobPosting> {
    return this.http.put<JobPosting>(this.apiEndpointsService.updateJobPostingEndpoint(), jobPosting);
  }

  deleteJobPosting(jobPostingId: string): Observable<any> {
    return this.http.delete(this.apiEndpointsService.deleteJobPostingEndpoint(jobPostingId));
  }

  archiveJob(jobPostingId: string): Observable<JobPosting> {
    return this.http.patch(this.apiEndpointsService.archiveJobPostingEndpoint(jobPostingId), null);
  }

  toggleArchiveJobs(jobIds: string[]): Observable<JobPosting> {
    return this.http.patch(this.apiEndpointsService.toggleArchiveJobsEndpoint(), jobIds);
  }

  getAllPendingActivationJobs(pageable: Pageable): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllPendingActivationJobsEndpoint(pageable));
  }

  validateJob(jobId: string): Observable<JobPosting> {
    return this.http.patch(this.apiEndpointsService.getValidateJobEndpoint(jobId), null);
  }
}
