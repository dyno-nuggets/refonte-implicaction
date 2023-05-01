import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../../core/services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize, take, takeUntil} from 'rxjs/operators';
import {Univers} from '../../../shared/enums/univers';
import {AlertService} from '../../../shared/services/alert.service';
import {Subject} from 'rxjs';
import {Alert, AlertType} from '../../../shared/models/alert';
import {LoginRequestPayload} from '../../models/login-request-payload';

@Component({
  templateUrl: './login-page.component.html',
})
export class LoginPageComponent implements OnInit, OnDestroy {

  alert: Alert;
  isLoading = false;

  private onDestroySubject = new Subject<void>();

  constructor(
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toaster: ToasterService,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this.alertService.onAlert()
      .pipe(takeUntil(this.onDestroySubject))
      .subscribe(alert => this.alert = alert);

    if (this.activatedRoute?.snapshot?.queryParams['signupSuccess']) {
      this.alert = {
        title: 'Votre compte a bien été créé',
        message: 'Votre inscription a bien été enregistrée. Elle doit maintenant être validée par un administrateur.',
        type: AlertType.SUCCESS
      };
    }
  }

  login(loginRequest: LoginRequestPayload): void {
    this.isLoading = true;
    this.authService.login(loginRequest)
      .pipe(finalize(() => this.isLoading = false), take(1))
      .subscribe({
        next: principal => this.redirectSuccess(principal.username),
        error: (err) => {
          if (err.error.errorMessage)
            this.alertService.error('Erreur', err.error.errorMessage, null);
        },
      });
  }

  private redirectSuccess(username: string): void {
    this.router.navigateByUrl(this.activatedRoute.snapshot.queryParams['returnUrl'] ?? Univers.HOME.url)
      .then(() => this.toaster.success(`Bienvenue ${username}`, 'vous êtes maintenant identifié'));
  }

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }
}
