import {EventEmitter, Injectable, Output} from '@angular/core';
import {Observable, of, throwError} from 'rxjs';
import {LocalStorageService} from 'ngx-webstorage';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {catchError, map, tap} from 'rxjs/operators';
import {SignupRequestPayload} from '../models/signup-request-payload';
import {LoginRequestPayload} from '../models/login-request-payload';
import {LoginResponse} from '../models/login-response';
import {HttpClient} from '@angular/common/http';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  @Output()
  loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output()
  currentUser: EventEmitter<User> = new EventEmitter();

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService,
    private localStorage: LocalStorageService
  ) {
  }

  get currentUser$(): Observable<User> {
    return this.currentUser.asObservable();
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
          // le localStorage ne  peut pas enregistrer d'objet alors il faut le serialiser
          this.localStorage.store('currentUser', JSON.stringify(loginResponse.currentUser));
          this.localStorage.store('refreshToken', loginResponse.refreshToken);
          this.localStorage.store('expiresAt', loginResponse.expiresAt);

          this.loggedIn.emit(true);
          this.currentUser.emit(loginResponse.currentUser);
          return true;
        }),
        catchError(() => of(false))
      );
  }


  logout(): Observable<boolean> {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getCurrentUser().username
    };

    this.http
      .post(this.apiEndpointsService.getLogoutEndpoint(), refreshTokenPayload, {responseType: 'text'})
      .subscribe(
        () => {
          // il n'y a rien à faire à la réception de la réponse car les opération de logout sont faites en dehors de l'observable
        },
        (error) => throwError(error)
      );
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('currentUser');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiresAt');

    this.loggedIn.emit(false);
    this.currentUser.emit(null);
    return of(true);
  }

  getJwtToken(): string {
    return this.localStorage.retrieve('authenticationToken');
  }

  refreshToken(): Observable<LoginResponse> {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getCurrentUser().username
    };

    return this.http
      .post<LoginResponse>(this.apiEndpointsService.getJwtRefreshTokenEndpoint(), refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.store('authenticationToken', response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }

  setCurrentUser(user: User): void {
    this.localStorage.store('currentUser', JSON.stringify(user));
    this.currentUser.emit(user);
  }

  getRefreshToken(): string {
    return this.localStorage.retrieve('refreshToken');
  }

  getCurrentUser(): User {
    return JSON.parse(this.localStorage.retrieve('currentUser'));
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }

  activateUser(activationKey: string): Observable<void> {
    return this.http.get<void>(this.apiEndpointsService.getActivateUserEndpoint(activationKey));
  }
}
