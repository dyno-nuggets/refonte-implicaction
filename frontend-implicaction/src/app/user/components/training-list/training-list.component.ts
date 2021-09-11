import {Component, Input, OnInit} from '@angular/core';
import {User} from '../../../../shared/models/user';

@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.scss']
})
export class TrainingListComponent implements OnInit {

  @Input()
  user: User;

  constructor() {
  }

  ngOnInit(): void {
  }

}
