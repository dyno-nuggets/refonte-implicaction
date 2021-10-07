import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from '../../shared/services/auth.service';
import {RoleEnumCode} from '../../shared/enums/role.enum';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private authService: AuthService
  ) {
  }

  private static getRoutePermissions(route: ActivatedRouteSnapshot): RoleEnumCode[] {
    if (route.data && route.data.allowedRoles) {
      return route.data.allowedRoles;
    }
    return null;
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const allowedRoles = AuthGuard.getRoutePermissions(route);
    if (this.authService.isLoggedIn()) {
      if (this.checkPermission(allowedRoles)) {
        return true;
      }
      this.router.navigate(['/unauthorized/']);
      return false;
    }
    this.router.navigate(['/auth/login'], {queryParams: {returnUrl: state.url}});
    return false;
  }

  private checkPermission(allowedUserRoles: RoleEnumCode[]): boolean {
    return this.authService
      .getCurrentUser()
      ?.roles
      ?.some(role => allowedUserRoles.includes(role)) ?? false;
  }

}
