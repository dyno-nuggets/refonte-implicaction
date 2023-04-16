import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AbstractControl, FormGroup, NonNullableFormBuilder, Validators} from '@angular/forms';
import {SignupRequestPayload} from '../../../models/signup-request-payload';
import {AuthFormValidatorService} from '../../../services/auth-form-validator.service';
import {SignupRequestForm} from '../../../models/forms/signup-request-form';

@Component({
  selector: 'app-signup-form',
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.scss']
})
export class SignupFormComponent implements OnInit {

  @Input() isLoading: boolean;

  @Output() submitForm = new EventEmitter<SignupRequestPayload>();

  signupForm!: FormGroup<SignupRequestForm>;
  submitted = false;

  constructor(
    private readonly fb: NonNullableFormBuilder,
    private readonly customFormValidator: AuthFormValidatorService,
  ) {
  }

  ngOnInit(): void {
    this.signupForm = this.fb.group(
      {
        username: this.fb.control('', [Validators.required]),
        email: this.fb.control('', [
          Validators.required,
          Validators.email,
        ]),
        lastname: this.fb.control('', Validators.required),
        password: this.fb.control('', [
          Validators.required,
          this.customFormValidator.passwordPatternValidator(),
        ]),
        confirmPassword: this.fb.control('', Validators.required),
        firstname: this.fb.control('', Validators.required),

      },
      {
        validators: [
          this.customFormValidator.matchPasswordValidator(
            'password',
            'confirmPassword'
          ),
        ],
      }
    );
  }

  get username(): AbstractControl<string, string> {
    return this.signupForm.get('username');
  }

  get email(): AbstractControl<string, string> {
    return this.signupForm.get('email');
  }

  get password(): AbstractControl<string, string> {
    return this.signupForm.get('password');
  }

  get confirmPassword(): AbstractControl<string, string> {
    return this.signupForm.get('confirmPassword');
  }

  get firstname(): AbstractControl<string, string> {
    return this.signupForm.get('firstname');
  }

  get lastname(): AbstractControl<string, string> {
    return this.signupForm.get('lastname');
  }

  submit() {
    if (this.signupForm.invalid) {
      return;
    }

    this.submitted = true;
    this.submitForm.emit({...this.signupForm.value});
  }
}
