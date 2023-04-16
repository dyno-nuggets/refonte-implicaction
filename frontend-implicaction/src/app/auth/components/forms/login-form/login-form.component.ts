import {Component, EventEmitter, Input, Output} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {LoginRequestPayload} from '../../../models/login-request-payload';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
})
export class LoginFormComponent {

  @Input() isLoading;

  @Output() formSubmit = new EventEmitter<LoginRequestPayload>();

  loginForm = new UntypedFormGroup({
    username: new UntypedFormControl('', Validators.required),
    password: new UntypedFormControl('', Validators.required)
  });

  submit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.formSubmit.emit({...this.loginForm.value});
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
