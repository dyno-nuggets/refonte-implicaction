import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-change-password-tab',
  templateUrl: './change-password-tab.component.html',
  styleUrls: ['./change-password-tab.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ChangePasswordTabComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

}
