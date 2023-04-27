import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AppService} from '../services/app.service';
import {AppStatusEnum} from '../../shared/enums/app-status-enum';

@Injectable({
  providedIn: 'root'
})
export class AppInitializedGuard implements CanActivate {

  constructor(
    private appService: AppService,
    private router: Router
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.appService.getValue()?.status === AppStatusEnum.INITIALIZED) {
      return true;
    }
    this.router.navigateByUrl('/auth/initialize');
  }

}
