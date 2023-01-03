import {Component, HostListener, Input} from '@angular/core';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {CreateGroupFormComponent} from '../create-group-form/create-group-form.component';
import {CreatePostFormComponent} from '../create-post-form/create-post-form.component';
import {AuthService} from "../../../shared/services/auth.service";
import {User} from "../../../shared/models/user";
import {RoleEnumCode} from "../../../shared/enums/role.enum";


@Component({
  selector: 'app-option-menu',
  templateUrl: './option-menu.component.html',
  styleUrls: ['./option-menu.component.scss']
})
export class OptionMenuComponent {

  userCurrent: User
  isAdmin: boolean;

  constructor(private sidebarService: SidebarService,
              private authService: AuthService) {

    this.userCurrent = this.authService.getCurrentUser()

    if (this.userCurrent.roles.includes(RoleEnumCode.ADMIN)) {
      this.isAdmin = true
    }

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
        title: 'Créer un forum',
        width: 735
      });
  }


}
