import {Component, Input, OnDestroy} from '@angular/core';
import {Profile} from '../../../../models/profile/profile';
import {ProfileContextService} from '../../../../../core/services/profile-context.service';
import {ProfileService} from '../../../../services/profile/profile.service';
import {ToasterService} from '../../../../../core/services/toaster.service';
import {Subject} from 'rxjs';
import {ProfileUpdateRequest} from '../../../../models/profile/profile-update-request';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'app-edit-profile-tab',
  templateUrl: './edit-profile-tab.component.html',
})
export class EditProfileTabComponent implements OnDestroy {

  @Input() profile: Profile;

  isLoading = false;

  private onDestroySubject = new Subject<void>();

  constructor(
    private profileService: ProfileService,
    private pcs: ProfileContextService,
    private toasterService: ToasterService
  ) {
  }

  updateProfile(updateRequest: ProfileUpdateRequest) {
    this.isLoading = true;
    console.log('request:', updateRequest);
    this.profileService.updateProfile(updateRequest)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe({
        // Mettre à jour l'attribut profile est inutile, car user-profile-page.component est abonné au pcs.
        // La variable sera donc mise à jour et transmise depuis ce composant.
        next: profile => this.pcs.profile = profile,
        error: () => this.toasterService.error('Oops', 'Une erreur est survenue lors de l\'enregistrement des informations de votre profil'),
        complete: () => this.toasterService.success('Mise à jour du profil', 'Les informations de votre profil ont été enregistrées')
      });
  }

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }
}
