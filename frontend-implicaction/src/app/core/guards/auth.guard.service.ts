import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from '../../shared/services/auth.service';
import {RoleEnum} from '../../shared/enums/role-enum.enum';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private authService: AuthService
  ) {
  }

  private static getRoutePermissions(route: ActivatedRouteSnapshot): RoleEnum[] {
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

  private checkPermission(allowedUserRoles: RoleEnum[]): boolean {
    return this.authService
      .getRoles()
      ?.some(role => allowedUserRoles.includes(role)) ?? false;
  }

}
