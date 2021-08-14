import {EventEmitter, Injectable, Output} from '@angular/core';
import {Observable, of, throwError} from 'rxjs';
import {LocalStorageService} from 'ngx-webstorage';
import {ApiHttpService} from '../../core/services/api-http.service';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {catchError, map, tap} from 'rxjs/operators';
import {SignupRequestPayload} from '../models/signup-request-payload';
import {LoginRequestPayload} from '../models/login-request-payload';
import {LoginResponse} from '../models/login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  @Output()
  loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output()
  username: EventEmitter<string> = new EventEmitter();

  constructor(
    private apiHttpService: ApiHttpService,
    private apiEndpointsService: ApiEndpointsService,
    private localStorage: LocalStorageService
  ) {
  }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.apiHttpService
      .post(
        this.apiEndpointsService.getSignUpEndpoint(),
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

          this.loggedIn.emit(true);
          this.username.emit(loginResponse.username);
          return true;
        }),
        catchError(() => of(false))
      );
  }

  logout(): Observable<boolean> {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUserName()
    };

    this.apiHttpService
      .post(this.apiEndpointsService.getLogoutEndpoint(), refreshTokenPayload, {responseType: 'text'})
      .subscribe(
        () => {
        },
        (error) => throwError(error)
      );
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiresAt');

    this.loggedIn.emit(false);
    this.username.emit(null);
    return of(true);
  }

  getJwtToken(): string {
    return this.localStorage.retrieve('authenticationToken');
  }

  refreshToken(): Observable<LoginResponse> {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUserName()
    };

    return (this.apiHttpService
      .post(this.apiEndpointsService.getJwtRefreshTokenEndpoint(), refreshTokenPayload) as Observable<LoginResponse>)
      .pipe(tap(response => {
        this.localStorage.store('authenticationToken', response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }

  getRefreshToken(): string {
    return this.localStorage.retrieve('refreshToken');
  }

  getUserName(): string {
    return this.localStorage.retrieve('username');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }
}
