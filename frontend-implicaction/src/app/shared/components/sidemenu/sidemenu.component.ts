import {Component, Input} from '@angular/core';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-sidemenu',
  templateUrl: './sidemenu.component.html',
  styleUrls: ['./sidemenu.component.scss']
})
export class SidemenuComponent {

  @Input() menuItems: MenuItem[];

  toggleCollapseParent(child: MenuItem, parent: MenuItem, expanded: boolean) {
    child.expanded = expanded;
    parent.expanded = parent.items.some(item => item.expanded);
  }

  toggleCollapse(menuItem: MenuItem) {
    this.menuItems.filter(i => i !== menuItem).forEach(i => i.expanded = false);
    menuItem.expanded = !menuItem.expanded;
  }
}
