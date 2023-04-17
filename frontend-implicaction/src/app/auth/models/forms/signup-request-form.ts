import {FormControl} from '@angular/forms';

export interface SignupRequestForm {
  username: FormControl<string>;
  email: FormControl<string>;
  password: FormControl<string>;
  confirmPassword: FormControl<string>;
  firstname: FormControl<string>;
  lastname: FormControl<string>;
}
