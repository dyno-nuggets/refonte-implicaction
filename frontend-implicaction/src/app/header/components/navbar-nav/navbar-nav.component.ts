import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Univers} from "../../../shared/enums/univers";

@Component({
  selector: 'app-navbar-nav',
  templateUrl: './navbar-nav.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavbarNavComponent {

  @Input() navItems: Univers[];

  universHome = Univers.HOME;

}
