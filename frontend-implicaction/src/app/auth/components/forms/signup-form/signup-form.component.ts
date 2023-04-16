import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormGroup, NonNullableFormBuilder, Validators} from '@angular/forms';
import {SignupRequestPayload} from '../../../models/signup-request-payload';
import {AuthFormValidatorService} from '../../../services/auth-form-validator.service';
import {SignupRequestForm} from '../../../models/forms/signup-request-form';
import {takeUntil} from 'rxjs/operators';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-signup-form',
  templateUrl: './signup-form.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SignupFormComponent implements OnInit, OnDestroy {

  @Input() isLoading: boolean;

  @Output() submitForm = new EventEmitter<SignupRequestPayload>();

  protected form!: FormGroup<SignupRequestForm>;
  protected submitted = false;
  protected usernameControls = new Map<string, string>([['required', 'Vous devez fournir un nom d\'utilisateur']]);
  protected emailControls = new Map<string, string>([
    ['required', 'Vous devez saisir une adresse email'],
    ['email', 'Vous devez saisir une adresse valide email valide'],
  ]);
  protected passwordControls = new Map<string, string>([
    ['required', 'Vous devez saisir un mot de passe'],
    [
      'invalidPassword',
      `Votre mot de passe doit être composé d'<span class="fw-bold">au moins 8 caractères</span>, d'<span class="fw-bold">au moins une lettre majuscule</span>,
        d'<span class="fw-bold">au mois une lettre minuscule</span> et d'au moins <span class="fw-bold">un chiffre</span>`
    ],
  ]);
  protected confirmPasswordControls = new Map<string, string>([
    ['required', 'Vous devez confirmer votre mot de passe'],
    ['passwordMismatch', 'Les mots de passe ne correspondent pas'],
  ]);
  protected firstnameControls = new Map<string, string>([
    ['required', 'Vous devez saisir votre prénom'],
  ]);
  protected lastnameControls = new Map<string, string>([
    ['required', 'Vous devez saisir votre nom de famille'],
  ]);

  private onDestroySubject = new Subject<void>();

  constructor(
    private readonly fb: NonNullableFormBuilder,
    private readonly customFormValidator: AuthFormValidatorService,
  ) {
  }

  ngOnInit(): void {
    this.initForm();
    this.form.valueChanges
      .pipe(takeUntil(this.onDestroySubject))
      .subscribe(() => this.submitted = false);
  }

  submit() {
    if (this.form.invalid) {
      return;
    }

    this.submitted = true;
    this.submitForm.emit({...this.form.value});
  }

  private initForm() {
    this.form = this.fb.group(
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

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }
}
