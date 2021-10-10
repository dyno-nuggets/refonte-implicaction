import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {CriteriaFilter} from '../models/criteria-filter';

@Injectable({
  providedIn: 'root'
})
export class FilterContextService {

  private behaviorSubject = new BehaviorSubject<CriteriaFilter>({});

  setFilter(criteria: CriteriaFilter): void {
    this.behaviorSubject.next(criteria);
  }

  observeFilter(): Observable<CriteriaFilter> {
    return this.behaviorSubject.asObservable();
  }
}
