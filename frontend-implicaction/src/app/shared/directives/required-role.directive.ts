import {Directive, Input, OnChanges, SimpleChanges, TemplateRef, ViewContainerRef} from '@angular/core';
import {AuthService} from "../../core/services/auth.service";
import {RoleEnumCode} from "../enums/role.enum";

@Directive({
  selector: '[appRequiredRole]'
})
export class RequiredRoleDirective implements OnChanges {

  requiredRole: RoleEnumCode;

  @Input('appRequiredRole')
  set isCurrentUser(requiredRole: RoleEnumCode) {
    this.requiredRole = requiredRole;
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
    if (!this.requiredRole) {
      return;
    }

    if (this.authService.getPrincipal()?.roles.includes(this.requiredRole)) {
      this.viewContainerRef.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainerRef.clear()
    }
  }

}
