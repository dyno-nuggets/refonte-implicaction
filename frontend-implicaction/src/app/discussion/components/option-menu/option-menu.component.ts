import {Component} from '@angular/core';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {GroupFormComponent} from '../group-form/group-form.component';

@Component({
  selector: 'app-option-menu',
  templateUrl: './option-menu.component.html',
  styleUrls: ['./option-menu.component.scss']
})
export class OptionMenuComponent {

  constructor(private sidebarService: SidebarService) {
  }

  openSidebarCreationGroup(): void {
    this.sidebarService
      .open({
        component: GroupFormComponent,
        title: 'Créer un groupe',
        width: 650
      });
  }
}
