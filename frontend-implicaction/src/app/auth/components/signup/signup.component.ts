import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SignupRequestPayload} from '../../models/signup-request-payload';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';

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
    this.signupRequestPayload = {
      login: '',
      email: '',
      password: ''
    };
  }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
    });
  }

  signup(): void {
    this.signupRequestPayload.email = this.signupForm.get('email').value;
    this.signupRequestPayload.login = this.signupForm.get('username').value;
    this.signupRequestPayload.password = this.signupForm.get('password').value;

    this.authService
      .signup(this.signupRequestPayload)
      .subscribe(
        () => this.router.navigate(['/auth/login'], {queryParams: {registered: 'true'}}),
        () => this.isError = true
      );
  }
}
