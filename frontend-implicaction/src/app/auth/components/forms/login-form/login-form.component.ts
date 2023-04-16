import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {LoginRequestPayload} from '../../../models/login-request-payload';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginFormComponent implements OnInit, OnDestroy {

  @Input() isLoading;

  @Output() submitForm = new EventEmitter<LoginRequestPayload>();

  protected form = new UntypedFormGroup({
    username: new UntypedFormControl('', Validators.required),
    password: new UntypedFormControl('', Validators.required)
  });
  protected submitted = false;
  protected usernameControls = new Map<string, string>([['required', 'Vous devez saisir votre nom d\'utilisateur']]);
  protected passwordControls = new Map<string, string>([['required', 'Vous devez saisir votre mot de passe']]);

  private onDestroySubject = new Subject<void>();

  ngOnInit(): void {
    this.initForm();
    this.form.valueChanges
      .pipe(takeUntil(this.onDestroySubject))
      .subscribe(() => this.submitted = false);
  }

  submit(): void {
    if (this.form.invalid) {
      return;
    }

    this.submitted = true;
    this.submitForm.emit({...this.form.value});
  }

  private initForm() {
    this.form = new UntypedFormGroup({
      username: new UntypedFormControl('', Validators.required),
      password: new UntypedFormControl('', Validators.required)
    });
  }

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }
}
