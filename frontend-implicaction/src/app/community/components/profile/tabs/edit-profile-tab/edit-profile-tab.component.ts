import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Profile} from "../../../../models/profile/profile";
import {FormGroup, NonNullableFormBuilder, Validators} from "@angular/forms";
import {ProfileContextService} from "../../../../../core/services/profile-context.service";
import {ProfileService} from "../../../../services/profile/profile.service";
import {ToasterService} from "../../../../../core/services/toaster.service";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {ProfileUpdateForm} from "../../../../models/profile/forms/profile-update-form";

@Component({
  selector: 'app-edit-profile-tab',
  templateUrl: './edit-profile-tab.component.html',
  styleUrls: ['./edit-profile-tab.component.scss']
})
export class EditProfileTabComponent implements OnInit, OnDestroy {

  @Input() profile: Profile;

  protected userForm!: FormGroup<ProfileUpdateForm>;
  protected isSubmitted = false;
  protected isLoading = false;

  private onDestroySubject = new Subject<undefined>();

  constructor(
    private fb: NonNullableFormBuilder,
    private profileService: ProfileService,
    private pcs: ProfileContextService,
    private toasterService: ToasterService
  ) {
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

  get firstname() {
    return this.userForm.get('firstname');
  }

  get lastname() {
    return this.userForm.get('lastname');
  }

  get email() {
    return this.userForm.get('email');
  }

  submit(): void {
    this.isSubmitted = false;
    if (this.userForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.profileService.updateProfile({...this.userForm.value})
      .subscribe({
        next: profile => {
          // Mettre à jour l'attribut profile est inutile, car user-profile-page.component est abonné au pcs. La variable sera donc mise à jour et transmise depuis ce composant.
          this.pcs.profile = profile;
          this.isSubmitted = true;
          this.toasterService.success('Mise à jour du profil', 'Les informations de votre profil ont été enregistrées');
        },
        error: () => this.toasterService.error('Oops', 'Une erreur est survenue lors de l\'enregistrement des informations de votre profil'),
        complete: () => this.isLoading = false
      });
  }

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }
}
