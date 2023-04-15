import {Component, Input, OnInit} from '@angular/core';
import {Profile} from "../../../../models/profile/profile";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ProfileContextService} from "../../../../../core/services/profile-context.service";
import {ProfileService} from "../../../../services/profile/profile.service";
import {ToasterService} from "../../../../../core/services/toaster.service";

@Component({
  selector: 'app-edit-profile-tab',
  templateUrl: './edit-profile-tab.component.html',
  styleUrls: ['./edit-profile-tab.component.scss']
})
export class EditProfileTabComponent implements OnInit {

  @Input() profile: Profile;

  userForm: FormGroup;
  isSubmitted = false;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private profileService: ProfileService,
    private pcs: ProfileContextService,
    private toasterService: ToasterService
  ) {
  }

  ngOnInit(): void {
    this.userForm = this.fb.group({
      // Infos Perso
      firstname: [this.profile.firstname, Validators.required],
      lastname: [this.profile.lastname, Validators.required],
      email: [this.profile.email, [Validators.required, Validators.email]],
      phoneNumber: [this.profile.phoneNumber],
      birthday: [this.profile.birthday],
      // Présentation
      presentation: [this.profile.presentation],
      purpose: [this.profile.purpose],
      expectation: [this.profile.expectation],
      contribution: [this.profile.contribution],
    });
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
      return
    }

    this.isLoading = true;
    this.profileService.updateProfile({username: this.profile.username, ...this.userForm.value})
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
}
