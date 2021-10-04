import {EventEmitter, Injectable, Output} from '@angular/core';
import {Observable, of, throwError} from 'rxjs';
import {LocalStorageService} from 'ngx-webstorage';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {catchError, map, tap} from 'rxjs/operators';
import {SignupRequestPayload} from '../models/signup-request-payload';
import {LoginRequestPayload} from '../models/login-request-payload';
import {LoginResponse} from '../models/login-response';
import {RoleEnumCode} from '../enums/role.enum';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  @Output()
  loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output()
  username: EventEmitter<string> = new EventEmitter();
  @Output()
  userId: EventEmitter<string> = new EventEmitter();

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService,
    private localStorage: LocalStorageService
  ) {
  }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.http
      .post(
        this.apiEndpointsService.getSignUpEndpoint(),
        signupRequestPayload,
        {responseType: 'text'}
      );
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<boolean> {
    return this.http
      .post<LoginResponse>(
        this.apiEndpointsService.getLoginEndpoint(),
        loginRequestPayload
      )
      .pipe(
        map(loginResponse => {
          this.localStorage.store('authenticationToken', loginResponse.authenticationToken);
          this.localStorage.store('username', loginResponse.username);
          this.localStorage.store('refreshToken', loginResponse.refreshToken);
          this.localStorage.store('expiresAt', loginResponse.expiresAt);
          this.localStorage.store('userId', loginResponse.userId);
          this.localStorage.store('roles', loginResponse.roles);

          this.loggedIn.emit(true);
          this.username.emit(loginResponse.username);
          this.userId.emit(loginResponse.userId);
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

    this.http
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
    this.localStorage.clear('roles');

    this.loggedIn.emit(false);
    this.username.emit(null);
    this.userId.emit(null);
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

    return (this.http
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

  getUserId(): string {
    // il faut appeler la méthode toString sinon getUserId renvoie un valeur de type number =-/
    return this.localStorage.retrieve('userId').toString();
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }

  getRoles(): RoleEnumCode[] {
    return this.localStorage.retrieve('roles');
  }

  activateUser(activationKey: string): Observable<void> {
    return this.http.get<void>(this.apiEndpointsService.getActivateUserEndpoint(activationKey));
  }
}
