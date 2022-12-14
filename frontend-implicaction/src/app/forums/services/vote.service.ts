import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Observable} from 'rxjs';
import {VotePayload} from '../model/vote-payload';

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  constructor(
    private http: HttpClient,
    private apiEndpointService: ApiEndpointsService
  ) {
  }

  vote(votePayload: VotePayload): Observable<any> {
    return this.http.post(this.apiEndpointService.getVoteEndpoint(), votePayload);
  }

}
