import {Injectable} from '@angular/core';
import {Comment} from '../model/comment';
import {CommentPayload} from '../model/comment-payload';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(
    private http: HttpClient,
    private apiEndpointService: ApiEndpointsService
  ) {
  }

  postComment(commentPayload: CommentPayload): Observable<Comment> {
    return this.http.post(this.apiEndpointService.postCommentEndpoint(), commentPayload);
  }
}
