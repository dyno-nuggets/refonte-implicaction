import {Component, Input} from '@angular/core';
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-community-sidemenu',
  templateUrl: './community-sidemenu.component.html',
  styleUrls: ['./community-sidemenu.component.scss']
})
export class CommunitySidemenuComponent {

  @Input() menuItems: MenuItem[];

}
