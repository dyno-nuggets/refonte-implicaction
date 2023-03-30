import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {ImplicactionEvent} from "../models/implicactionEvent";

@Injectable({
  providedIn: 'root'
})
export class ImplicactionEventService {

  getLatestEvents(): Observable<ImplicactionEvent[]> {
    return of([
      {
        id: 1,
        title: `Conférence Général de Villiers à l'UCO d'Angers`,
        date: new Date('2021-12-01')
      },
      {
        id: 2,
        title: `Conférence Général de Villiers à l'UCO d'Angers`,
        date: new Date('2021-09-21')
      },
      {
        id: 3,
        title: `Conférence Général de Villiers à l'UCO d'Angers`,
        date: new Date('2021-12-01')
      }
    ])
  }
}
