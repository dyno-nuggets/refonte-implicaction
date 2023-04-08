import {Directive, Input, OnChanges, SimpleChanges, TemplateRef, ViewContainerRef} from '@angular/core';
import {RoleEnumCode} from "../enums/role.enum";
import {AuthService} from "../../core/services/auth.service";

@Directive({
  selector: '[appAnyRoleMatches]'
})
export class AnyRoleMatchesDirective implements OnChanges {

  requiredRoles: RoleEnumCode[];

  @Input('appAnyRoleMatches')
  set isCurrentUser(roles: RoleEnumCode[]) {
    this.requiredRoles = roles;
  }

  constructor(
    private viewContainerRef: ViewContainerRef,
    private templateRef: TemplateRef<any>,
    private authService: AuthService
  ) {
    this.validateAccess();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.validateAccess();
  }

  private validateAccess(): void {
    if (!this.requiredRoles) {
      return;
    }

    const userRoles = this.authService.getPrincipal()?.roles;
    const atLeastOneCommonRole = this.atLeastOneCommonRole(userRoles, this.requiredRoles);
    if (atLeastOneCommonRole) {
      this.viewContainerRef.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainerRef.clear()
    }
  }

  private atLeastOneCommonRole(userRoles: RoleEnumCode[], requiredRoles: RoleEnumCode[]): boolean {
    if (!requiredRoles || !requiredRoles.length) {
      return true;
    }

    if (!userRoles) {
      return false;
    }

    // si au moins un rôle de l'utilisateur correspond à un des rôles requis, alors la condition sera vérifiée
    return userRoles?.filter(role => requiredRoles?.includes(role)).length > 0;
  }

}
