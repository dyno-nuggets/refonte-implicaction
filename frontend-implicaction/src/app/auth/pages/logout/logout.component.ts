import {Component} from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";
import {ToasterService} from "../../../core/services/toaster.service";
import {Router} from "@angular/router";
import {Univers} from "../../../shared/enums/univers";

@Component({
  template: ''
})
export class LogoutComponent {

  constructor(
    private authService: AuthService,
    private toasterService: ToasterService,
    private router: Router,
  ) {
    this.authService.logout().subscribe(() =>
      this.router.navigateByUrl(Univers.HOME.url)
        .then(() => this.toasterService.success('Succès', 'Vous êtes maintenant déconnecté'))
    );
  }
}
