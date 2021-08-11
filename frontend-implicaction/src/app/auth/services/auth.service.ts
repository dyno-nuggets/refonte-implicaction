import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SignupRequestPayload} from '../models/signup-request-payload';
import {Observable} from 'rxjs';
import {LoginRequestPayload} from '../models/login-request-payload';
import {LoginResponse} from '../models/login-response';
import {map} from 'rxjs/operators';
import {LocalStorageService} from 'ngx-webstorage';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  static readonly BASE_URL = '/api/auth';

  constructor(private http: HttpClient, private localStorage: LocalStorageService) {
  }

  // TODO: faire en sorte de renvoyer autre chose que Observable<any>
  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.http.post(`${AuthService.BASE_URL}/signup`, signupRequestPayload, {responseType: 'text'});
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<boolean> {
    return this.http
      .post<LoginResponse>(`${AuthService.BASE_URL}/login`, loginRequestPayload)
      .pipe(
        map(loginResponse => {
          this.localStorage.store('authenticationToken', loginResponse.authenticationToken);
          this.localStorage.store('username', loginResponse.username);
          this.localStorage.store('refreshToken', loginResponse.refreshToken);
          this.localStorage.store('expiresAt', loginResponse.expiresAt);
          return true;
        }));
  }
}
