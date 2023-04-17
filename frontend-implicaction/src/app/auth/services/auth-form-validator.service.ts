import {Injectable} from '@angular/core';
import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class AuthFormValidatorService {

  private static readonly PASSWORD_PATTERN = '^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$';

  passwordPatternValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const regex = new RegExp(AuthFormValidatorService.PASSWORD_PATTERN);
      const validPattern = regex.test(control.value);
      return validPattern ? null : {invalidPassword: true};
    };
  }

  matchPasswordValidator(password: string, confirmPassword: string) {
    return (formGroup: AbstractControl): ValidationErrors | null => {
      const passwordControl = formGroup.get(password);
      const confirmPasswordControl = formGroup.get(confirmPassword);

      if (!passwordControl || !confirmPasswordControl) {
        return null;
      }

      if (confirmPasswordControl.errors && !confirmPasswordControl.errors['passwordMismatch']) {
        return null;
      }

      if (passwordControl.value !== confirmPasswordControl.value) {
        confirmPasswordControl.setErrors({passwordMismatch: true});
        return {passwordMismatch: true};
      } else {
        confirmPasswordControl.setErrors(null);
        return null;
      }
    };
  }
}
