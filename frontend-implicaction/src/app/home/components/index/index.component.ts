import {Component} from '@angular/core';
import {Univers} from '../../../shared/enums/univers';


@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent {

  univers = Univers;

}
