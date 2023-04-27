import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-community-sidemenu',
  templateUrl: './community-sidemenu.component.html',
  styleUrls: ['./community-sidemenu.component.scss']
})
export class CommunitySidemenuComponent implements OnInit {

  @Input() menuItems: any[];

  constructor() {
  }

  ngOnInit(): void {
  }

}
