import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-user-info-display',
  templateUrl: './user-info-display.component.html',
  styleUrls: ['./user-info-display.component.scss']
})
export class UserInfoDisplayComponent implements OnInit {

  @Input()
  value: string;

  constructor() { }

  ngOnInit(): void {
  }

}
