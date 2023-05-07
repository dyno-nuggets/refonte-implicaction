import {Component, Input, OnInit} from '@angular/core';
import {MenuItem} from "primeng/api";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-sidemenu',
  templateUrl: './sidemenu.component.html',
  styleUrls: ['./sidemenu.component.scss']
})
export class SidemenuComponent implements OnInit {

  @Input() menuItems: MenuItem[];

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    this.router.events.subscribe(routerEvent => {
      if (routerEvent instanceof NavigationEnd) {
        // Get your url
        console.log(routerEvent.url);
        console.log(this.menuItems.find(item => !!item.items && item.items.find(i => i.url && this.router.isActive(i.url, false))));
      }
    });


  }

}
