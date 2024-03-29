import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Router} from '@angular/router';
import {AlertService} from '../../shared/services/alert.service';
import {tap} from 'rxjs/operators';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(
    private router: Router,
    private alertService: AlertService
  ) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request)
      .pipe(
        tap(
          () => {
            // on ne fait rien, car on veut seulement récupérer les erreurs
          },
          error => {
            // on ne s'intéresse qu'aux erreurs http différentes des 401 et 403 (car déjà gérées par le jwt-interceptor)
            if (error instanceof HttpErrorResponse && ![401, 403].includes(error.status)) {
              // FIXME: pourquoi le back renvoie du string au lieu d'une erreur si le username/email est déjà utilisé lors d'un signup
              const errorMessage = typeof error.error === 'string' ?
                JSON.parse(error.error).errorMessage : error.error.errorMessage ?? error.message;
              this.alertService.error('Erreur', errorMessage, error.status);
              return throwError(() => error);
            }
          }
        ));
  }
}
