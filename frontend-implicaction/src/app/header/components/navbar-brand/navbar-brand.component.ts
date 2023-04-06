import {ChangeDetectionStrategy, Component} from '@angular/core';
import {Univers} from "../../../shared/enums/univers";

@Component({
  selector: 'app-navbar-brand',
  templateUrl: './navbar-brand.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavbarBrandComponent {

  readonly homeUrl = Univers.HOME.url;
}
