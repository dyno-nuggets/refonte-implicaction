import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BaseContextServiceService<T> {

  protected behaviorSubject = new BehaviorSubject<T>({} as T);

  public get observe$(): Observable<T> {
    return this.behaviorSubject.asObservable();
  }

  public notify(obj: T): void {
    this.behaviorSubject.next(obj);
  }
}
