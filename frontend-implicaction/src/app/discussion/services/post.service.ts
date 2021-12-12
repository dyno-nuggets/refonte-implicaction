import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Pageable} from '../../shared/models/pageable';
import {Post} from '../model/post';
import {PostPayload} from '../model/post-payload';

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

  getById(postId: string): Observable<Post> {
    return this.http.get(this.endpointsService.getPostEndpoint(postId));
  }

  getLatestPosts(postsCount: number): Observable<Post[]> {
    return this.http.get<Post[]>(this.endpointsService.getLatestPostsEndpoint(postsCount));
  }

  getCommentsByPostId(pageable: Pageable, postId: string): Observable<any> {
    return this.http.get(this.endpointsService.getPostCommentsEndpoint(pageable, postId));
  }

  createPost(postPayload: PostPayload): Observable<Post> {
    return this.http.post(this.endpointsService.createPostEndpoint(), postPayload);
  }
}
