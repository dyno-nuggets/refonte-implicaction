import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiHttpService} from '../../core/services/api-http.service';
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

  getAll(pageable: Pageable): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllJobEndpoint(pageable));
  }
}
