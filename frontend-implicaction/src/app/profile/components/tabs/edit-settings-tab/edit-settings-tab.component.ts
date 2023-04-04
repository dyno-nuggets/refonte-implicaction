import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-edit-settings-tab',
  templateUrl: './edit-settings-tab.component.html',
  styleUrls: ['./edit-settings-tab.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EditSettingsTabComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

}
