import {Component, Input} from '@angular/core';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-sidemenu',
  templateUrl: './sidemenu.component.html',
  styleUrls: ['./sidemenu.component.scss']
})
export class SidemenuComponent {

  @Input() menuItems: MenuItem[];

  /**
   * Pour développer le parent, on regarde s’il possède au moins un enfant 'active' cad si l’attribut
   * isActive est vrai.
   */
  onChildActiveChange(child: MenuItem, parent: MenuItem, isActive: boolean) {
    child.expanded = isActive;
    parent.expanded = parent.items.some(item => item.expanded);
  }


  toggleCollapse(menuItem: MenuItem) {
    this.menuItems
      .filter(item => item !== menuItem && item.expanded)
      .forEach(item => {
        item.expanded = false;
      });

    menuItem.expanded = !menuItem.expanded;
  }
}
