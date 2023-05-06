import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../community/services/profile/user.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-user-counter-widget',
  templateUrl: './user-counter-widget.component.html',
})
export class UserCounterWidgetComponent implements OnInit {

  userCount$: Observable<number>;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userCount$ = this.userService.getEnabledUsersCount();
  }

}
