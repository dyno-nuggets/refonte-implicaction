import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Alert, AlertType} from '../models/alert';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  private subject = new BehaviorSubject<Alert>(null);

  constructor() {
  }

  onAlert(): Observable<Alert> {
    return this.subject.asObservable();
  }

  success(title: string, message: string): void {
    this.alert({title, message, type: AlertType.SUCCESS});
  }

  error(title: string, message: string): void {
    this.alert({title, message, type: AlertType.DANGER});
  }

  clear(): void {
    this.subject.next(null);
  }

  private alert(alert: Alert): void {
    this.subject.next(alert);
  }
}
