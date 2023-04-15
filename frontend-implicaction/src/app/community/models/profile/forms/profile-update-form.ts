import {FormControl} from "@angular/forms";

export interface ProfileUpdateForm {
  username: FormControl<string>;
  firstname: FormControl<string>;
  lastname: FormControl<string>;
  email: FormControl<string>;
  birthday: FormControl<string>;
  hobbies: FormControl<string>;
  purpose: FormControl<string>;
  presentation: FormControl<string>;
  expectation: FormControl<string>;
  contribution: FormControl<string>;
  phoneNumber: FormControl<string>;
}
