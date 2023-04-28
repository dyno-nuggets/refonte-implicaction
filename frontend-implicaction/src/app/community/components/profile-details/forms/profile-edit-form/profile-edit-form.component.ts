import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {AbstractControl, NonNullableFormBuilder, Validators} from '@angular/forms';
import {Profile} from '../../../../models/profile';
import {ProfileUpdateRequest} from '../../../../models/profile-update-request';
import {BaseFormComponent} from '../../../../../shared/components/base-form/base-form.component';

@Component({
  selector: 'app-profile-edit-form',
  templateUrl: './profile-edit-form.component.html',
  styleUrls: ['./profile-edit-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileEditFormComponent extends BaseFormComponent<ProfileUpdateRequest> {

  @Input() profile!: Profile;

  constructor(private fb: NonNullableFormBuilder) {
    super();
  }

  protected initForm(): void {
    this.form = this.fb.group({
      // Infos Perso
      username: this.fb.control({
        value: this.profile.username,
        disabled: true
      }, Validators.required),
      firstname: this.fb.control(this.profile.firstname, Validators.required),
      lastname: this.fb.control(this.profile.lastname, Validators.required),
      email: this.fb.control(this.profile.email, [
        Validators.required,
        Validators.email
      ]),
      phoneNumber: this.fb.control(this.profile.phoneNumber),
      birthday: this.fb.control(this.profile.birthday),
      // Présentation
      presentation: this.fb.control(this.profile.presentation),
      purpose: this.fb.control(this.profile.purpose),
      expectation: this.fb.control(this.profile.expectation),
      contribution: this.fb.control(this.profile.contribution),
      hobbies: this.fb.control(this.profile.hobbies),
    });
  }

  protected get firstname(): AbstractControl<string, string> {
    return this.form.get('firstname');
  }

  protected get lastname(): AbstractControl<string, string> {
    return this.form.get('lastname');
  }

  protected get email(): AbstractControl<string, string> {
    return this.form.get('email');
  }

  protected getData(): ProfileUpdateRequest {
    return {username: this.profile.username, ...this.form.value};
  }
}
