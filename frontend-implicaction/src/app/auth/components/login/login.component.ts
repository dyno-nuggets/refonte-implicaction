import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../shared/services/auth.service';
import {LoginRequestPayload} from '../../../shared/models/login-request-payload';
import {ActivatedRoute, Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';
import {Univers} from '../../../shared/enums/univers';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginRequestPayload: LoginRequestPayload;
  isLoading = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toaster: ToasterService
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
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  login(): void {
    if (!this.loginForm.valid) {
      return;
    }

    this.isLoading = true;

    this.loginRequestPayload.username = this.loginForm.get('username').value;
    this.loginRequestPayload.password = this.loginForm.get('password').value;

    this.authService
      .login(this.loginRequestPayload)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(isLoginSuccess => {
        if (isLoginSuccess) {
          this.redirectSuccess();
        }
      });
  }

  private redirectSuccess(): void {
    this.activatedRoute
      .queryParams
      .subscribe(
        params => this.router
          .navigateByUrl(params.returnUrl || Univers.HOME.url)
          .then(() => this.toaster.success('Bienvenue', 'vous êtes maintenant connecté')),
        error => console.log(error)
      );
  }
}
