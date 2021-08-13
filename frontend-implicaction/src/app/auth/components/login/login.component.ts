import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../core/services/auth.service';
import {LoginRequestPayload} from '../../../core/models/login-request-payload';
import {ActivatedRoute, Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginRequestPayload: LoginRequestPayload;
  registerSuccessMessage: string;
  isError: boolean;

  constructor(
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toaster: ToasterService,
  ) {
    this.loginRequestPayload = {
      username: '',
      password: ''
    };

    this.activatedRoute
      .queryParams
      .subscribe(params => {
        if (params.registered && params.registered === 'true') {
          this.registerSuccessMessage = 'Your registration must be validated by an administrator. Please check your email';
        }
      });
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  login(): void {
    this.loginRequestPayload.username = this.loginForm.get('username').value;
    this.loginRequestPayload.password = this.loginForm.get('password').value;
    this.authService
      .login(this.loginRequestPayload)
      .subscribe(isLoginSuccess => {
        if (isLoginSuccess) {
          this.isError = false;
          this.router
            .navigateByUrl('/')
            .then(() => this.toaster.success('Success', 'Login Successful'));
        } else {
          this.isError = true;
        }
      });
  }
}
