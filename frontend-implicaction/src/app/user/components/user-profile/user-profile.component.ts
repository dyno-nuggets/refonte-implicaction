import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../../shared/models/user';
import {ToasterService} from '../../../core/services/toaster.service';
import {ActivatedRoute} from '@angular/router';
import {UserContexteService} from '../../../shared/services/user-contexte.service';
import {Subscription} from 'rxjs';
import {AuthService} from '../../../shared/services/auth.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit, OnDestroy {

  user: User = {};
  canEdit = false;

  private subscription: Subscription;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private toasterService: ToasterService,
    private route: ActivatedRoute,
    private userContexteService: UserContexteService
  ) {
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      const userId = paramMap.get('userId');
      this.userService
        .getUserById(userId)
        .subscribe(
          user => {
            this.user = user;
            this.canEdit = user.id.toString() === this.authService.getUserId();
            this.userContexteService.setUser(user);
          },
          () => this.toasterService.error('oops', 'Une erreur est survenue !')
        );
    });
    this.subscription = this.userContexteService
      .observeUser()
      .subscribe(user => this.user = user);
  }

}
