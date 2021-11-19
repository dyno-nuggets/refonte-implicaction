import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class FilterContextService<T> {

  private behaviorSubject = new BehaviorSubject<T>({} as T);

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  get criteria(): T {
    return this.behaviorSubject.value;
  }

  set criteria(criteria: T) {
    this.behaviorSubject.next(criteria);
  }

  observe(): Observable<T> {
    return this.behaviorSubject.asObservable();
  }

  /**
   * Ajoute les valeurs des clés de filtrage à l'url sous forme de query param. Les attributs de filtre dont les valeurs
   * sont vides ou nulles sont au préalable filtrées
   * @param filter les clés de filtrage
   */
  updateRouteQueryParams(filter: any): void {
    const sanitizedParam = {};
    Object.entries(filter)
      // TODO: améliorer avec un reduce
      .forEach(([key, value]) => sanitizedParam[key] = value ? value : null);

    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: sanitizedParam,
        queryParamsHandling: 'merge'
      });
  }
}
