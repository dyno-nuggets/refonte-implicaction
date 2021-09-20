import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SignupRequestPayload} from '../../../shared/models/signup-request-payload';
import {AuthService} from '../../../shared/services/auth.service';
import {Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {RoleEnum} from '../../../shared/enums/role-enum.enum';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  signupForm: FormGroup;
  signupRequestPayload: SignupRequestPayload;
  isError: boolean;

  constructor(
    private authService: AuthService,
    private toaster: ToasterService,
    private router: Router,
  ) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/']);
    } else {
      this.signupRequestPayload = {
        username: '',
        email: '',
        password: '',
        nicename: '',
        roles: [RoleEnum.USER]
      };
    }
  }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
      repeatPassword: new FormControl('', Validators.required),
      nicename: new FormControl('', Validators.required)
    });
  }

  signup(): void {

    if (!this.signupForm.valid || !this.confirmPassword()) {
      return;
    }

    this.signupRequestPayload.email = this.signupForm.get('email')?.value;
    this.signupRequestPayload.username = this.signupForm.get('username')?.value;
    this.signupRequestPayload.password = this.signupForm.get('password')?.value;
    this.signupRequestPayload.nicename = this.signupForm.get('nicename')?.value;

    this.authService
      .signup(this.signupRequestPayload)
      .subscribe(
        () => this.router.navigate(['/auth/login'], {queryParams: {registered: 'true'}}),
        () => this.isError = true
      );
  }

  private confirmPassword(): boolean {
    return this.signupForm.get('password')?.value === this.signupForm.get('repeatPassword')?.value;
  }
}
