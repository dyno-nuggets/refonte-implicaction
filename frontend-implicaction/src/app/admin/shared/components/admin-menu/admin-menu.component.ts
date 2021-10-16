import {Component, OnInit} from '@angular/core';
import {Univers} from '../../../../shared/enums/univers';
import {RoleEnumCode} from '../../../../shared/enums/role.enum';
import {AuthService} from '../../../../shared/services/auth.service';

interface RoleBaseMenuItem {
  label: string;
  link: string;
  classIcon?: string;
  badge?: string;
  roles: RoleEnumCode[];
}

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.scss']
})
export class AdminMenuComponent implements OnInit {

  allowedMenuItems: RoleBaseMenuItem[] = [];

  private menuItems: RoleBaseMenuItem[] = [
    {
      label: 'Dashboard',
      link: `/${Univers.ADMIN.url}/dashboard`,
      classIcon: 'pi pi-file',
      roles: [RoleEnumCode.ADMIN]
    },
    {
      label: 'Utilisateurs',
      link: `/${Univers.ADMIN.url}/users`,
      classIcon: 'pi pi-user',
      roles: [RoleEnumCode.ADMIN]
    },
    {
      label: 'Offres',
      link: `/${Univers.ADMIN.url}/jobs`,
      classIcon: 'far fa-newspaper',
      roles: [RoleEnumCode.ADMIN]
    },
    {
      label: 'Entreprises',
      link: `/${Univers.ADMIN.url}/companies`,
      classIcon: 'pi pi-id-card',
      roles: [RoleEnumCode.ADMIN]
    },
  ];

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    const currentUserRoles = this.authService.getCurrentUser()?.roles;
    this.allowedMenuItems = this.getAllowedMenuItems(currentUserRoles);
  }

  private getAllowedMenuItems(roles: RoleEnumCode[] = []): RoleBaseMenuItem[] {
    return this.menuItems
      .filter(item => !item.roles || item.roles.filter(roleAllowed => roles.includes(roleAllowed)).length > 0);
  }

}
