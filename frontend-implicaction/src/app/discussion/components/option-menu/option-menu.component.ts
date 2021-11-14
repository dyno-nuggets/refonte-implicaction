import {Component} from '@angular/core';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {CreateGroupFormComponent} from '../create-group-form/create-group-form.component';
import {CreatePostFormComponent} from '../create-post-form/create-post-form.component';

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
        component: CreateGroupFormComponent,
        title: 'Créer un groupe',
        width: 650
      });
  }

  openSidebarCreationPost(): void {
    this.sidebarService
      .open({
        component: CreatePostFormComponent,
        title: 'Créer une discussion',
        width: 735
      });
  }
}
