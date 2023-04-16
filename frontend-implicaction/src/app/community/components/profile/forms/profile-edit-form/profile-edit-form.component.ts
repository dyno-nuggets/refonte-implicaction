import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {AbstractControl, FormGroup, NonNullableFormBuilder, Validators} from '@angular/forms';
import {takeUntil} from 'rxjs/operators';
import {Profile} from '../../../../models/profile/profile';
import {Subject} from 'rxjs';
import {ProfileUpdateRequest} from '../../../../models/profile/profile-update-request';
import {ProfileUpdateForm} from '../../../../models/profile/forms/profile-update-form';

@Component({
  selector: 'app-profile-edit-form',
  templateUrl: './profile-edit-form.component.html',
  styleUrls: ['./profile-edit-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProfileEditFormComponent implements OnInit, OnDestroy {
  @Input() profile!: Profile;
  @Input() isLoading;
  @Input() isSubmitted;

  @Output() formSubmit: EventEmitter<ProfileUpdateRequest> = new EventEmitter<ProfileUpdateRequest>();

  userForm!: FormGroup<ProfileUpdateForm>;

  private onDestroySubject = new Subject<void>();

  constructor(private fb: NonNullableFormBuilder) {
  }

  ngOnInit(): void {
    this.userForm = this.fb.group({
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

    this.userForm.valueChanges
      .pipe(takeUntil(this.onDestroySubject))
      .subscribe(() => this.isSubmitted = false);
  }

  get firstname(): AbstractControl<string, string> {
    return this.userForm.get('firstname');
  }

  get lastname(): AbstractControl<string, string> {
    return this.userForm.get('lastname');
  }

  get email(): AbstractControl<string, string> {
    return this.userForm.get('email');
  }

  submit() {
    if (this.userForm.invalid) {
      return;
    }

    this.formSubmit.emit({username: this.profile.username, ...this.userForm.value});
  }

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }
}
