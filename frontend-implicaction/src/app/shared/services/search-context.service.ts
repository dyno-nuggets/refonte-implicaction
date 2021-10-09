import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {BaseFilter} from '../models/base-filter';

@Injectable({
  providedIn: 'root'
})
export class SearchContextService {

  private behaviorSubject = new BehaviorSubject<BaseFilter>({});

  setFilter(filter: BaseFilter): void {
    this.behaviorSubject.next(filter);
  }

  observeFilter(): Observable<BaseFilter> {
    return this.behaviorSubject.asObservable();
  }
}
