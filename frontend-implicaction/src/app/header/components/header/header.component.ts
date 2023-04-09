import {ChangeDetectionStrategy, Component, Input, OnChanges, OnDestroy, SimpleChanges} from '@angular/core';
import {AuthService} from '../../../core/services/auth.service';
import {Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {Univers} from '../../../shared/enums/univers';
import {RoleEnumCode} from '../../../shared/enums/role.enum';
import {Subject} from "rxjs";
import {Profile} from "../../../community/models/profile/profile";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent implements OnChanges, OnDestroy {

  @Input() currentProfile?: Profile;
  @Input() userRoles?: RoleEnumCode[];

  univers = Univers;
  navItems: Univers[];

  private onDestroySubject = new Subject<undefined>();

  constructor(
    private authService: AuthService,
    private router: Router,
    private toaster: ToasterService,
  ) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['userRoles']) {
      this.navItems = this.getItemsMenu(this.userRoles);
    }
  }

  logout(): void {
    this.authService
      .logout()
      .subscribe(
        () => this.router.navigateByUrl('/')
          .then(() => this.toaster.success('Succès', 'Vous êtes maintenant déconnecté'))
      );
  }

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }

  /**
   * @return la liste des univers à afficher dans le menu de navigation en fonction des rôles de l'utilisateur
   */
  private getItemsMenu(userRoles: RoleEnumCode[]): Univers[] {
    return userRoles?.length
      ? Univers.all().filter(univers =>
        // on ne garde que les univers qui doivent apparaître dans le menu
        univers.isMenuItem
        // on n'affiche pas l'espace entreprise pour les utilisateurs identifiés
        && univers !== Univers.COMPANY_AREA
        // on garde seulement ceux dont au moins un des rôles nécessaires correspond à un des rôles de l'utilisateur
        && this.hasCommonRoles(userRoles, univers.roles))
      : [Univers.HOME, Univers.COMPANY_AREA];
  }

  private hasCommonRoles(userRoles: RoleEnumCode[], requiredRoles: RoleEnumCode[]): boolean {
    if (!requiredRoles?.length) {
      return true;
    }

    if (!userRoles) {
      return false;
    }

    // si au moins un rôle de l'utilisateur correspond à un des rôles requis, alors la condition sera vérifiée
    return userRoles.some(role => requiredRoles?.includes(role));
  }
}
