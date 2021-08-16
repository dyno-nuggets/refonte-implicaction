import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../shared/services/auth.service';
import {LoginRequestPayload} from '../../../shared/models/login-request-payload';
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
  isRegisterSuccess: boolean;
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
        this.isRegisterSuccess = params.registered && params.registered === 'true';
      });
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  login(): void {
    if (!this.loginForm.valid) {
      return;
    }
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
