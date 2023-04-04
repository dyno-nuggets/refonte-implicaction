import {ChangeDetectionStrategy, Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {User} from '../../models/user';
import {Univers} from '../../enums/univers';
import {RoleEnumCode} from '../../enums/role.enum';
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent implements OnChanges {

  @Input() currentUser: User;
  isLoggedIn: boolean;
  allowedUnivers: Univers[] = [];
  isAdmin = false;
  univers = Univers;
  constant = Constants;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toaster: ToasterService,
  ) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.isLoggedIn = !!this.currentUser;
    console.log(this.currentUser);
    this.allowedUnivers = Univers.getAllowedUniversForMenu(this.currentUser?.roles);
    // si l’utilisateur est identifié, on ne souhaite pas afficher l’espace entreprise dans le menu
    if (this.isLoggedIn) {
      this.allowedUnivers = this.allowedUnivers.filter(u => u != Univers.COMPANY_AREA);
    }
    this.isAdmin = this.currentUser?.roles.includes(RoleEnumCode.ADMIN);
  }

  logout(): void {
    this.authService
      .logout()
      .subscribe(() => this.router
        .navigateByUrl('/')
        .then(() => this.toaster.success('Succès', 'Vous êtes maintenant déconnecté'))
      );
  }
}
