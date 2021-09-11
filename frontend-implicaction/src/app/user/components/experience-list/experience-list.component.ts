import {Component, Input, OnInit} from '@angular/core';
import {User} from '../../../shared/models/user';

@Component({
  selector: 'app-experience-list',
  templateUrl: './experience-list.component.html',
  styleUrls: ['./experience-list.component.scss']
})
export class ExperienceListComponent implements OnInit {

  @Input()
  user: User;

  constructor() {
  }

  ngOnInit(): void {
  }

}
