import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {AuthService} from '../services/auth.service';
import {catchError, filter, switchMap, take} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class JwtInterceptor implements HttpInterceptor {

  isTokenRefreshing = false;
  refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);

  constructor(private authService: AuthService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const jwtToken = this.authService.getJwtToken();
    if (jwtToken) {
      JwtInterceptor.addToken(request, jwtToken);
    }
    return next.handle(request)
      .pipe(
        catchError(error => {
          if (error instanceof HttpErrorResponse && error.status === 403) {
            return this.handleAuthErrors(request, next);
          } else {
            return throwError(error);
          }
        })
      );
  }

  private static addToken(request: HttpRequest<unknown>, jwtToken: string): HttpRequest<unknown> {
    return request.clone({
      headers: request.headers.set('Authorization', `Bearer ${jwtToken}`)
    });
  }

  private handleAuthErrors(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (!this.isTokenRefreshing) {
      this.isTokenRefreshing = true;
      this.refreshTokenSubject.next(null);

      return this.authService.refreshToken()
        .pipe(switchMap(refreshTokenResponse => {
            this.isTokenRefreshing = false;
            this.refreshTokenSubject.next(refreshTokenResponse.authenticationToken);
            return next.handle(JwtInterceptor.addToken(request, refreshTokenResponse.authenticationToken));
          })
        );
    } else {
      return this.refreshTokenSubject.pipe(
        filter(result => result !== null),
        take(1),
        switchMap(() => {
          return next.handle(JwtInterceptor.addToken(request, this.authService.getJwtToken()));
        })
      );
    }
  }
}
