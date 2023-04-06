import {Component, HostListener, Input} from '@angular/core';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../core/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';
import {Constants} from '../../../config/constants';
import {ProfileUpdateRequest} from "../../models/profile-update-request";
import {ProfileService} from "../../services/profile.service";
import {Profile} from "../../models/profile";

@Component({
  selector: 'app-sidebar-profile',
  templateUrl: './sidebar-profile.component.html',
  styleUrls: ['./sidebar-profile.component.scss']
})
export class SidebarProfileComponent {

  readonly constant = Constants;

  @Input()
  readOnly = true;

  @Input()
  profile: Profile;
  profileCopy: Profile;
  isEditing = false;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private profileService: ProfileService,
    private toasterService: ToasterService
  ) {
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Escape' && this.isEditing) {
      this.toggleModeEdition();
    }
  }

  toggleModeEdition(): void {
    if (!this.isEditing) {
      // on clone le profil afin de pouvoir rollback
      this.profileCopy = {...this.profile};
    } else {
      this.profile = {...this.profileCopy};
    }
    this.isEditing = !this.isEditing;
  }

  updatePersonalInfo(): void {
    const updateRequest: ProfileUpdateRequest = {
      username: this.profile.username,
      firstname: this.profile.firstname,
      lastname: this.profile.lastname,
      birthday: this.profile.birthday,
      hobbies: this.profile.hobbies,
      purpose: this.profile.purpose,
      presentation: this.profile.presentation,
      expectation: this.profile.expectation,
      contribution: this.profile.contribution,
      phoneNumber: this.profile.phoneNumber,
      email: this.profile.email
    };

    this.profileService
      .updateProfile(updateRequest)
      .pipe(finalize(() => this.isEditing = false))
      .subscribe(
        profile => this.profile = profile,
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données'),
        () => this.toasterService.success('Ok', 'Le changement des données a bien été effectué')
      );
  }

}
