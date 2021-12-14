import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {ToasterService} from '../../../core/services/toaster.service';
import {User} from '../../models/user';
import {Univers} from '../../enums/univers';
import {RoleEnumCode} from '../../enums/role.enum';
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {

  isLoggedIn: boolean;
  currentUser: User;
  allowedUnivers: Univers[] = [];
  isAdmin = false;
  displayProfile = false;
  univers = Univers;
  constant = Constants;

  private subscription: Subscription;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toaster: ToasterService,
  ) {
  }

  ngOnInit(): void {
    this.subscription = this.authService
      .loggedIn
      .subscribe(isLoggedIn => this.isLoggedIn = isLoggedIn)
      .add(
        this.authService
          .currentUser$
          .subscribe(currentUser => {
            this.currentUser = currentUser;
            this.allowedUnivers = Univers.getAllowedUnivers(this.currentUser?.roles);
            this.isAdmin = this.currentUser?.roles.includes(RoleEnumCode.ADMIN);
            this.displayProfile = this.currentUser?.roles.includes(RoleEnumCode.USER);
          })
      );
    this.isLoggedIn = this.authService.isLoggedIn();
    this.currentUser = this.authService.getCurrentUser();
    this.allowedUnivers = Univers.getAllowedUnivers(this.currentUser?.roles);
    this.isAdmin = this.currentUser?.roles.includes(RoleEnumCode.ADMIN);
    this.displayProfile = this.currentUser?.roles.includes(RoleEnumCode.USER);
  }

  logout(): void {
    this.authService
      .logout()
      .subscribe(() => this.router
        .navigateByUrl('/')
        .then(() => this.toaster.success('Succès', 'Vous êtes maintenant déconnecté'))
      );
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
