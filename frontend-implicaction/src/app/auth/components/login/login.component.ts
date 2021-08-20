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
  showAlert: boolean;
  alert: { title: string, severity: string, body: string };

  constructor(
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toaster: ToasterService,
  ) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/']);
    } else {
      this.loginRequestPayload = {
        username: '',
        password: ''
      };

      this.activatedRoute
        .queryParams
        .subscribe(params => {
          this.showAlert = params.registered && params.registered === 'true';
          this.alert = {
            title: 'Félicitation',
            body: 'Votre inscription a bien été enregistrée. Elle doit maintenant être validée par un administrateur.',
            severity: 'success'
          };
        });
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

    this.loginRequestPayload.username = this.loginForm.get('username').value;
    this.loginRequestPayload.password = this.loginForm.get('password').value;

    this.authService
      .login(this.loginRequestPayload)
      .subscribe(isLoginSuccess => {
        if (isLoginSuccess) {
          this.showAlert = false;
          this.redirectAndToastSuccess();
        } else {
          this.showAlert = true;
          this.alert = {
            title: 'Erreur',
            body: `Nom d'utilisateur ou mot de passe incorrect.`,
            severity: 'danger'
          };
        }
      });
  }

  private redirectAndToastSuccess(): void {
    this.activatedRoute
      .queryParams
      .subscribe(
        params => this.router
          .navigateByUrl(params.returnUrl || '/')
          .then(() => this.toaster.success('Success', 'Login Successful')),
        error => console.log(error)
      );
  }
}
