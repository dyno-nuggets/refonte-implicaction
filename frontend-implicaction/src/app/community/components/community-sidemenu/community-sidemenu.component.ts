import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-community-sidemenu',
  templateUrl: './community-sidemenu.component.html',
  styleUrls: ['./community-sidemenu.component.scss']
})
export class CommunitySidemenuComponent {

  @Input() menuItems: any[];

}
