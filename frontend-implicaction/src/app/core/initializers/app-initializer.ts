import {catchError} from 'rxjs/operators';
import {of} from 'rxjs';
import {AppService} from '../services/app.service';

export function appInitializer(appService: AppService) {
  return () => appService.getApp()
    .pipe(
      catchError(() => of())
    );
}
