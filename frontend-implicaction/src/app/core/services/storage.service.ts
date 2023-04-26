import {Injectable} from '@angular/core';
import {LocalStorageService} from 'ngx-webstorage';
import {LoginResponse} from '../../auth/models/login-response';
import {Principal} from '../../shared/models/principal';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private static readonly REFRESH_TOKEN_KEY = 'refreshtoken';
  private static readonly JWT_TOKEN_KEY = 'authenticationToken';
  private static readonly EXPIRE_AT_KEY = 'expiresAt';
  private static readonly PRINCIPAL_KEY = 'principal';

  constructor(private localStorage: LocalStorageService) {
  }

  storeLoginResponse(loginResponse: LoginResponse): void {
    const {authenticationToken, refreshToken, expiresAt} = loginResponse;
    this.store(StorageService.JWT_TOKEN_KEY, authenticationToken);
    this.store(StorageService.REFRESH_TOKEN_KEY, refreshToken);
    this.store(StorageService.EXPIRE_AT_KEY, expiresAt);
  }

  storePrincipal(principal: Principal): void {
    this.store(StorageService.PRINCIPAL_KEY, JSON.stringify(principal));
  }

  clearLogin(): void {
    this.localStorage.clear(StorageService.JWT_TOKEN_KEY);
    this.localStorage.clear(StorageService.REFRESH_TOKEN_KEY);
    this.localStorage.clear(StorageService.EXPIRE_AT_KEY);
    this.localStorage.clear(StorageService.PRINCIPAL_KEY);
  }

  clearPrincipal(): void {
    return this.clear(StorageService.PRINCIPAL_KEY);
  }

  getPrincipal(): string {
    return this.retrieve(StorageService.PRINCIPAL_KEY);
  }

  getRefreshToken(): string {
    return this.retrieve(StorageService.REFRESH_TOKEN_KEY);
  }

  getJwtToken(): string {
    return this.retrieve(StorageService.JWT_TOKEN_KEY);
  }

  private retrieve(key: string): any {
    return this.localStorage.retrieve(key);
  }

  private clear(key: string): void {
    return this.localStorage.clear(key);
  }

  private store(key: string, value: any): void {
    !!key && this.localStorage.store(key, value);
  }
}
