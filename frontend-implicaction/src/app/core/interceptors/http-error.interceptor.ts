import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {Router} from '@angular/router';
import {AlertService} from '../../shared/services/alert.service';
import {catchError, retry} from 'rxjs/operators';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router, private alertService: AlertService) {
  }

  private static handleServerSideError(error: HttpErrorResponse): boolean {
    let handled = false;

    // on ignore les erreurs 401 et 403 qui sont gérées par le jwt-interceptor
    switch (error.status) {
      case 401:
        handled = true;
        break;
      case 403:
        handled = true;
        break;
    }

    return handled;
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let handled = false;

    return next.handle(request)
      .pipe(
        retry(1),
        catchError(returnedError => {
          let errorMessage = null;
          if (returnedError instanceof HttpErrorResponse) {
            // FIXME: trouver pourquoi le back renvoie du string au lieu d'une erreur si le username/email est
            // déjà utilisé lors d'un signup
            errorMessage = typeof returnedError.error === 'string' ?
              JSON.parse(returnedError.error).errorMessage : returnedError.error.errorMessage;
            this.alertService.error('Erreur', errorMessage);
            handled = HttpErrorInterceptor.handleServerSideError(returnedError);
          }

          console.error(errorMessage);

          if (!handled) {
            if (errorMessage) {
              return throwError(errorMessage);
            } else {
              return throwError('Une erreur inattendue est survenue');
            }
          } else {
            return of(returnedError);
          }

        })
      );
  }
}
