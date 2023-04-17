import {ChangeDetectionStrategy, Component} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {LoginRequestPayload} from '../../../models/login-request-payload';
import {BaseFormComponent} from '../../../../shared/components/base-form/base-form.component';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginFormComponent extends BaseFormComponent<LoginRequestPayload> {
  protected usernameControls = new Map<string, string>([['required', 'Vous devez saisir votre nom d\'utilisateur']]);
  protected passwordControls = new Map<string, string>([['required', 'Vous devez saisir votre mot de passe']]);

  protected initForm() {
    this.form = new UntypedFormGroup({
      username: new UntypedFormControl('', Validators.required),
      password: new UntypedFormControl('', Validators.required)
    });
  }
}
