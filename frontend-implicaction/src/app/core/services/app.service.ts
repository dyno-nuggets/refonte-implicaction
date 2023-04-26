import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {App} from '../../shared/models/app';
import {ApiEndpointsService} from './api-endpoints.service';
import {tap} from 'rxjs/operators';
import {AppStatusEnum} from '../../shared/enums/app-status-enum';

@Injectable({
  providedIn: 'root'
})
export class AppService {
  private appSubject = new BehaviorSubject<App>(null);

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }

  getApp(): Observable<App> {
    if (this.appSubject.getValue()) {
      return this.appSubject.asObservable();
    }

    return this.http.get<App>(this.apiEndpointsService.getAppEndpoint())
      .pipe(tap(app => this.appSubject.next(app)));
  }

  getValue(): App {
    return this.appSubject.value;
  }

  setStatus(status: AppStatusEnum) {
    this.appSubject.next({...this.getValue(), status});
  }
}
