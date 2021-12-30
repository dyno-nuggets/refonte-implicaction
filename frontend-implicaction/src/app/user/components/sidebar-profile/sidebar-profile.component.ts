import {Component, HostListener, Input} from '@angular/core';
import {User} from '../../../shared/models/user';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';
import {Constants} from '../../../config/constants';

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
  user: User;
  userCopie: User;
  isEditing = false;

  constructor(
    private userService: UserService,
    private authService: AuthService,
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
      // on clone le user afin de pouvoir rollback
      this.userCopie = {...this.user};
    } else {
      this.user = {...this.userCopie};
    }
    this.isEditing = !this.isEditing;
  }

  updatePersonalInfo(): void {
    this.userService
      .updateUser(this.user)
      .pipe(finalize(() => this.isEditing = false))
      .subscribe(
        user => this.user = user,
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données'),
        () => this.toasterService.success('Ok', 'Le changement des données a bien été effectué')
      );
  }

  onFileChange(event): void {
    const reader = new FileReader();

    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);

      reader.onload = () => {
        this.user.imageUrl = reader.result as string;
        const formData = new FormData();
        formData.set('file', file, file.filename);
        this.userService
          .updateUserImage(formData)
          .subscribe(
            user => {
              this.authService.setCurrentUser(user);
              // on "valide" le changement d'image en mettant à jour la valeur userCopie.imageUrl de sorte que si on
              // rollback les modifications de l'utilisateur, l'image soit tjs affichée
              this.userCopie.imageUrl = this.user.imageUrl;
            },
            () => {
              this.toasterService.error('Oops', 'Une erreur est survenue lors de la modification de votre image de profil');
              // on on rollback l'image de l'utilisateur
              this.user.imageUrl = this.userCopie.imageUrl;
            },
            () => this.toasterService.success('Succès', 'Votre image de profil a été mise à jour avec succès.')
          );
      };
    }
  }
}
