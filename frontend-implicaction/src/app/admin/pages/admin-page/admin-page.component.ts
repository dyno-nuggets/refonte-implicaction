import {Component} from '@angular/core';
import {Univers} from "../../../shared/enums/univers";
import {MenuItem} from "primeng/api";

@Component({
  templateUrl: './admin-page.component.html',
})
export class AdminPageComponent {

  menuItems: MenuItem[] = [
    {
      label: 'Dashboard',
      routerLink: `/${Univers.ADMIN.url}/dashboard`,
      icon: 'fas fa-tachometer-alt',
      routerLinkActiveOptions: {exact: true}
    },
    {
      label: 'Utilisateurs',
      routerLink: `/${Univers.ADMIN.url}/users`,
      icon: 'fa fa-user',
      routerLinkActiveOptions: {exact: true}
    },
    {
      label: 'Offres',
      routerLink: `/${Univers.ADMIN.url}/jobs`,
      icon: 'fas fa-briefcase',
      routerLinkActiveOptions: {queryParams: 'subset'}
    },
    {
      label: 'Entreprises',
      routerLink: `/${Univers.ADMIN.url}/companies`,
      icon: 'fas fa-building',
      routerLinkActiveOptions: {exact: true}
    },
    {
      label: 'Forum',
      routerLink: `/${Univers.ADMIN.url}/forum`,
      icon: 'fas fa-comments',
      routerLinkActiveOptions: {queryParams: 'subset', exact: false},
      items: [
        {
          label: 'Catégories',
          routerLink: `/${Univers.ADMIN.url}/forum`,
          routerLinkActiveOptions: {exact: true},
        },
        {
          label: 'Messages',
          routerLink: `/${Univers.ADMIN.url}/forum`,
          routerLinkActiveOptions: {exact: true},
        }
      ]
    },
  ];
}
