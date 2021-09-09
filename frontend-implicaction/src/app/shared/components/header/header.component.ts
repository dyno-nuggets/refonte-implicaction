import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {ToasterService} from '../../../core/services/toaster.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {

  isLoggedIn: boolean;
  username: string;
  userId: string;

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
        this.authService.username.subscribe(username => this.username = username)
      )
      .add(
        this.authService.userId.subscribe(userId => this.userId = userId)
      );
    this.isLoggedIn = this.authService.isLoggedIn();
    this.username = this.authService.getUserName();
    this.userId = this.authService.getUserId();
  }

  logout(): void {
    this.authService
      .logout()
      .subscribe(() => {
        this.router
          .navigateByUrl('/')
          .then(() => {
            this.toaster.success('Success', 'You are now logged out!');
          });
      });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
