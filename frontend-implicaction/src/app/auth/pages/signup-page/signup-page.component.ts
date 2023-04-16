import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../../core/services/auth.service';
import {Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize, take, takeUntil} from 'rxjs/operators';
import {AlertService} from '../../../shared/services/alert.service';
import {Alert} from '../../../shared/models/alert';
import {SignupRequestPayload} from '../../models/signup-request-payload';
import {Subject} from 'rxjs';

@Component({
  templateUrl: './signup-page.component.html',
})
export class SignupPageComponent implements OnInit, OnDestroy {

  isLoading = false;
  alert: Alert;

  private onDestroySubject = new Subject<void>();

  constructor(
    private authService: AuthService,
    private toaster: ToasterService,
    private router: Router,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this.alertService.onAlert()
      .pipe(takeUntil(this.onDestroySubject))
      .subscribe(alert => this.alert = alert);
  }

  signup(signupPaylod: SignupRequestPayload): void {
    this.authService
      .signup(signupPaylod)
      .pipe(finalize(() => this.isLoading = false), take(1))
      .subscribe(() => this.router.navigate(['/auth/login'], {queryParams: {signupSuccess: true}}));
  }

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }
}
