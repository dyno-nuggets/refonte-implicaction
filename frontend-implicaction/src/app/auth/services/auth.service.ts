import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SignupRequestPayload} from '../models/signup-request-payload';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  static readonly BASE_URL = '/api/auth';

  constructor(private http: HttpClient) {
  }

  // TODO: faire en sorte de renvoyer autre chose que Observable<any>
  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.http.post(`${AuthService.BASE_URL}/signup`, signupRequestPayload, {responseType: 'text'});
  }
}
