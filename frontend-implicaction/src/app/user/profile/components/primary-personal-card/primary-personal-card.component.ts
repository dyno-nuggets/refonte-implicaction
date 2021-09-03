import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../../../shared/models/user";

@Component({
  selector: 'app-primary-personal-card',
  templateUrl: './primary-personal-card.component.html',
  styleUrls: ['./primary-personal-card.component.scss']
})
export class PrimaryPersonalCardComponent implements OnInit {

  @Input()
  user: User;

  constructor() { }

  ngOnInit(): void {
  }

}
