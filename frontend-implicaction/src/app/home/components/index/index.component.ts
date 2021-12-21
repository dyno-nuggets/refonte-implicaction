import {Component} from '@angular/core';
import {Univers} from '../../../shared/enums/univers';


@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent {

  univers = Univers;
  events = [
    {
      title: `Conférence Général de Villiers à l'UCO d'Angers`,
      date: new Date('2021-12-01')
    },
    {
      title: `Conférence Général de Villiers à l'UCO d'Angers`,
      date: new Date('2021-09-21')
    },
    {
      title: `Conférence Général de Villiers à l'UCO d'Angers`,
      date: new Date('2021-12-01')
    }
  ];

}
