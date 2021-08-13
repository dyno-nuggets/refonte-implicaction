import {Injectable} from '@angular/core';
import {SignupRequestPayload} from '../models/signup-request-payload';
import {Observable} from 'rxjs';
import {LoginRequestPayload} from '../models/login-request-payload';
import {LocalStorageService} from 'ngx-webstorage';
import {ApiHttpService} from '../../core/services/api-http.service';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {map} from 'rxjs/operators';
import {LoginResponse} from '../models/login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private apiHttpService: ApiHttpService,
    private apiEndpointsService: ApiEndpointsService,
    private localStorage: LocalStorageService
  ) {
  }

  // TODO: faire en sorte de renvoyer autre chose que Observable<any>
  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.apiHttpService
      .post(
        this.apiEndpointsService.getSignUpEndPoint(),
        signupRequestPayload,
        {responseType: 'text'}
      );
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<boolean> {
    return (this.apiHttpService
      .post(
        this.apiEndpointsService.getLoginEndpoint(),
        loginRequestPayload
      ) as Observable<LoginResponse>)
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
