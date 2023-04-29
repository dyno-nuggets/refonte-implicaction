import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-user-status-badge',
  templateUrl: './user-status-badge.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserStatusBadgeComponent implements OnInit {

  @Input() enabled: boolean;

  status: string;
  text: string;

  ngOnInit(): void {
    if (this.enabled) {
      this.status = 'success';
      this.text = 'actif';
    } else {
      this.status = 'warning';
      this.text = 'inactif';
    }
  }

}
