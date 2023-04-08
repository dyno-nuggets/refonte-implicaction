import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, of, throwError} from 'rxjs';
import {ApiEndpointsService} from './api-endpoints.service';
import {catchError, map, tap} from 'rxjs/operators';
import {SignupRequestPayload} from '../../auth/models/signup-request-payload';
import {LoginRequestPayload} from '../../auth/models/login-request-payload';
import {LoginResponse} from '../../auth/models/login-response';
import {HttpClient} from '@angular/common/http';
import {StorageService} from "./storage.service";
import jwt_decode from 'jwt-decode';
import {Principal} from "../../shared/models/principal";
import {RoleEnumCode} from "../../shared/enums/role.enum";


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  principalSubject: BehaviorSubject<Principal>;

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService,
    private storageService: StorageService
  ) {
    const jwtToken = this.storageService.getJwtToken();
    this.principalSubject = new BehaviorSubject<Principal>(jwtToken ? this.decodeToken(jwtToken) : null);
  }

  get principal$(): Observable<Principal> {
    return this.principalSubject.asObservable();
  }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.http.post(
      this.apiEndpointsService.getSignUpEndpoint(),
      signupRequestPayload,
      {responseType: 'text'}
    );
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<Principal> {
    return this.http.post<LoginResponse>(this.apiEndpointsService.getLoginEndpoint(), loginRequestPayload)
      .pipe(
        map(loginResponse => {
          const principal = this.decodeToken(loginResponse.authenticationToken);
          this.storageService.storeLoginResponse(loginResponse);
          this.storeAndEmitPrincipal(principal);
          return principal;
        }),
        catchError(() => of(null))
      );
  }

  private storeAndEmitPrincipal(principal: Principal): void {
    this.storageService.storePrincipal(principal);
    this.principalSubject.next(principal);
  }


  logout(): Observable<boolean> {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getPrincipal()?.username
    };

    this.http
      .post(this.apiEndpointsService.getLogoutEndpoint(), refreshTokenPayload, {responseType: 'text'})
      .subscribe(
        () => ({}),
        (error) => throwError(error)
      );

    this.storageService.clearLogin();
    this.clearPrincipal();
    return of(true);
  }

  getJwtToken(): string {
    return this.storageService.getJwtToken();
  }

  refreshToken(): Observable<LoginResponse> {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getPrincipal()?.username
    };

    return this.http.post<LoginResponse>(
      this.apiEndpointsService.getJwtRefreshTokenEndpoint(),
      refreshTokenPayload
    )
      .pipe(tap(response => this.storageService.storeLoginResponse(response)));
  }

  getRefreshToken(): string {
    return this.storageService.getRefreshToken();
  }

  getPrincipal(): Principal {
    return this.principalSubject.getValue();
  }

  isLoggedIn(): boolean {
    return !!this.getJwtToken();
  }

  private decodeToken(token: string): Principal {
    const tokenAsAnObject = jwt_decode(token) as any;
    return {
      username: tokenAsAnObject.sub,
      roles: this.rolesAsStringToRoleEnumCodes(tokenAsAnObject.scopes)
    };
  }

  private clearPrincipal(): void {
    this.storeAndEmitPrincipal(null);
    this.storageService.clearPrincipal();
  }

  private rolesAsStringToRoleEnumCodes(rolesAsString: string): RoleEnumCode[] {
    if (!rolesAsString) {
      return []
    }
    return rolesAsString.split(',') as RoleEnumCode[];
  }
}
