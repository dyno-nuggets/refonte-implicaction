import {ChangeDetectionStrategy, Component} from '@angular/core';
import {Univers} from "../../../shared/enums/univers";

@Component({
  selector: 'app-navbar-register-options',
  templateUrl: './navbar-register-options.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavbarRegisterOptionsComponent {

  univers = Univers;

}
