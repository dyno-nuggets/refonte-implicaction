import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FilterContextService<T> {

  private behaviorSubject = new BehaviorSubject<T>({} as T);

  setFilter(criteria: T): void {
    this.behaviorSubject.next(criteria);
  }

  observeFilter(): Observable<T> {
    return this.behaviorSubject.asObservable();
  }
}
