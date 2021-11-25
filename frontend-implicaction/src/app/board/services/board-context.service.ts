import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {JobApplication} from '../models/job-application';

@Injectable({
  providedIn: 'root'
})
export class BoardContextService {

  private behaviorSubject = new BehaviorSubject<JobApplication>(null);

  get apply(): JobApplication {
    return this.behaviorSubject.value;
  }

  set apply(apply: JobApplication) {
    this.behaviorSubject.next(apply);
  }

  observe(): Observable<JobApplication> {
    return this.behaviorSubject.asObservable();
  }
}
