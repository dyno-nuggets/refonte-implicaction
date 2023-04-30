import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../../../shared/models/user";

@Component({
  selector: 'app-user-status-badge',
  templateUrl: './user-status-badge.component.html',
})
export class UserStatusBadgeComponent implements OnInit {

  @Input() user: User;

  status: string;
  text: string;

  ngOnInit(): void {
    if (this.user.enabled) {
      this.status = 'success';
      this.text = 'actif';
    } else {
      this.status = 'warning';
      this.text = 'inactif';
    }
  }

}
