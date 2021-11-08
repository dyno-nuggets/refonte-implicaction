import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Pageable} from '../../shared/models/pageable';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(
    private http: HttpClient,
    private endpointsService: ApiEndpointsService
  ) {
  }

  getAllPosts(pageable: Pageable): Observable<any> {
    return this.http.get(this.endpointsService.getAllPostsEndpoint(pageable));
  }
}
