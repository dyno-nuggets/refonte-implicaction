import {Component, OnDestroy, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../shared/services/auth.service';
import {LoginRequestPayload} from '../../../shared/models/login-request-payload';
import {ActivatedRoute, Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize, take, tap} from 'rxjs/operators';
import {Univers} from '../../../shared/enums/univers';
import {AlertService} from "../../../shared/services/alert.service";
import {Subscription} from "rxjs";
import {Alert, AlertType} from "../../../shared/models/alert";

@Component({
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm: UntypedFormGroup;
  loginRequestPayload: LoginRequestPayload;
  isLoading = false;
  alert: Alert;

  private subscription: Subscription;

  constructor(
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toaster: ToasterService,
    private alertService: AlertService
  ) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate([Univers.HOME.url]);
    } else {
      this.loginRequestPayload = {
        username: '',
        password: ''
      };
    }
  }

  ngOnInit(): void {
    this.loginForm = new UntypedFormGroup({
      username: new UntypedFormControl('', Validators.required),
      password: new UntypedFormControl('', Validators.required)
    });
    this.subscription = this.alertService.onAlert()
      .pipe(tap(console.log))
      .subscribe(alert => this.alert = alert);
    if (this.activatedRoute.snapshot.queryParams['signupSuccess']) {
      this.alert = {
        title: 'Votre compte a bien été créé',
        message: 'Votre inscription a bien été enregistrée. Elle doit maintenant être validée par un administrateur.',
        type: AlertType.SUCCESS
      }
    }
  }

  login(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.loginRequestPayload = {
      username: this.loginForm.get('username').value,
      password: this.loginForm.get('password').value
    };
    this.authService
      .login(this.loginRequestPayload)
      .pipe(finalize(() => this.isLoading = false), take(1))
      .subscribe(isLogged => isLogged && this.redirectSuccess());
  }

  private redirectSuccess(): void {
    this.router
      .navigateByUrl(this.activatedRoute.snapshot.queryParams['returnUrl'] ?? Univers.HOME.url)
      .then(() => this.toaster.success(`Bienvenue ${this.loginRequestPayload.username}`, 'vous êtes maintenant identifié'))
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}