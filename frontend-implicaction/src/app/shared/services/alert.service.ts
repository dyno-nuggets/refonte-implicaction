import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {Alert, AlertType} from '../models/alert';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  private subject = new Subject<Alert>();

  onAlert(): Observable<Alert> {
    return this.subject.asObservable();
  }

  success(title: string, message: string): void {
    this.alert({title, message, type: AlertType.SUCCESS});
  }

  error(title: string, message: string, code: number): void {
    this.alert({title, message, type: AlertType.DANGER, code});
  }

  clear(): void {
    this.subject.next(null);
  }

  private alert(alert: Alert): void {
    this.subject.next(alert);
  }
}
