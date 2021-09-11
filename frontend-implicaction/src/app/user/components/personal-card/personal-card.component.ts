import {Component, Input, OnInit} from '@angular/core';
import {User} from '../../../shared/models/user';

@Component({
  selector: 'app-personal-card',
  templateUrl: './personal-card.component.html',
  styleUrls: ['./personal-card.component.scss']
})
export class PersonalCardComponent implements OnInit {

  @Input()
  user: User;

  constructor() {
  }

  ngOnInit(): void {
  }

}
