import {Component, OnDestroy, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {SignupRequestPayload} from '../../models/signup-request-payload';
import {AuthService} from '../../../core/services/auth.service';
import {Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize, take} from 'rxjs/operators';
import {AlertService} from '../../../shared/services/alert.service';
import {Univers} from '../../../shared/enums/univers';
import {Subscription} from 'rxjs';
import {Alert} from '../../../shared/models/alert';

@Component({
  templateUrl: './signup.component.html',
})
export class SignupComponent implements OnInit, OnDestroy {

  signupForm: UntypedFormGroup;
  signupRequestPayload: SignupRequestPayload;
  isLoading = false;
  alert: Alert;

  private subscription: Subscription;

  constructor(
    private authService: AuthService,
    private toaster: ToasterService,
    private router: Router,
    private alertService: AlertService
  ) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate([Univers.HOME.url]);
    } else {
      this.signupRequestPayload = {
        username: '',
        email: '',
        password: '',
        firstname: '',
        lastname: ''
      };
    }
  }

  ngOnInit(): void {
    this.signupForm = new UntypedFormGroup({
      username: new UntypedFormControl('', Validators.required),
      email: new UntypedFormControl('', [Validators.required, Validators.email]),
      password: new UntypedFormControl('', Validators.required),
      repeatPassword: new UntypedFormControl('', Validators.required),
      firstname: new UntypedFormControl('', Validators.required),
      lastname: new UntypedFormControl('', Validators.required),
    });
    this.subscription = this.alertService.onAlert()
      .subscribe(alert => this.alert = alert);
  }

  signup(): void {
    if (this.signupForm.invalid || !this.confirmPassword()) {
      return;
    }

    this.isLoading = true;
    this.signupRequestPayload.email = this.signupForm.get('email')?.value;
    this.signupRequestPayload.username = this.signupForm.get('username')?.value;
    this.signupRequestPayload.password = this.signupForm.get('password')?.value;
    this.signupRequestPayload.firstname = this.signupForm.get('firstname')?.value;
    this.signupRequestPayload.lastname = this.signupForm.get('lastname')?.value;

    this.authService
      .signup(this.signupRequestPayload)
      .pipe(finalize(() => this.isLoading = false), take(1))
      .subscribe(() => this.router.navigate(['/auth/login'], {queryParams: {signupSuccess: true}}));
  }

  private confirmPassword(): boolean {
    return this.signupForm.get('password')?.value === this.signupForm.get('repeatPassword')?.value;
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
