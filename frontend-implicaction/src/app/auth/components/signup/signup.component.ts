import {Component, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {SignupRequestPayload} from '../../../shared/models/signup-request-payload';
import {AuthService} from '../../../shared/services/auth.service';
import {Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';
import {AlertService} from '../../../shared/services/alert.service';
import {Univers} from '../../../shared/enums/univers';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  signupForm: UntypedFormGroup;
  signupRequestPayload: SignupRequestPayload;
  isError: boolean;
  isLoading = false;

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
  }

  signup(): void {

    if (!this.signupForm.valid || !this.confirmPassword()) {
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
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        () => {
          this.router.navigate(['/auth/login'])
            .then(() => this.alertService.success('Félicitations', 'Votre inscription a bien été enregistrée. Elle doit maintenant être validée par un administrateur.'));
        },
        () => this.isError = true
      );
  }

  private confirmPassword(): boolean {
    return this.signupForm.get('password')?.value === this.signupForm.get('repeatPassword')?.value;
  }
}
