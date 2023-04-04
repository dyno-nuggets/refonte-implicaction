import {Component, Input} from '@angular/core';
import {Constants} from "../../../config/constants";
import {Profile} from "../../models/profile";
import {ProfileService} from "../../services/profile.service";
import {ToasterService} from "../../../core/services/toaster.service";

@Component({
  selector: 'app-profil-pic-with-hover',
  templateUrl: './profil-pic-with-hover.component.html',
  styleUrls: ['./profil-pic-with-hover.component.scss']
})
export class ProfilPicWithHoverComponent {

  readonly USER_IMAGE_DEFAULT_URI = Constants.USER_IMAGE_DEFAULT_URI;

  @Input()
  profile!: Profile;


  constructor(
    private profileService: ProfileService,
    private toasterService: ToasterService
  ) {
  }

  onFileChange(event): void {
    const reader = new FileReader();

    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);

      reader.onload = () => {
        this.profile.imageUrl = reader.result as string;
        const formData = new FormData();
        formData.set('file', file, file.filename);
        this.profileService
          .updateUserImage(this.profile.username, formData)
          .subscribe(
            // on "valide" le changement d’image en mettant à jour la valeur userCopie.imageUrl de sorte que si on
            // rollback les modifications de l’utilisateur, l’image soit tjs affichée
            () => ({}),
            () => {
              this.toasterService.error('Oops', 'Une erreur est survenue lors de la modification de votre image de profil');
            },
            () => this.toasterService.success('Succès', 'Votre image de profil a été mise à jour avec succès.')
          );
      };
    }
  }
}
