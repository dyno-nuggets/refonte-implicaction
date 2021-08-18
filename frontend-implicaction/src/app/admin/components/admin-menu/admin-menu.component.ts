import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.scss']
})
export class AdminMenuComponent implements OnInit {

  menuItems: { label: string, link: string, classIcon?: string, badge?: string }[] = [
    {label: 'Pages', link: '/admin/pages', classIcon: 'pi pi-file', badge: '12'},
    {label: 'Posts', link: '/admin/posts', classIcon: 'pi pi-book', badge: '33'},
    {label: 'Pages', link: '/admin/users', classIcon: 'pi pi-user', badge: '203'},
  ];

  constructor() {
  }

  ngOnInit(): void {
  }

}
