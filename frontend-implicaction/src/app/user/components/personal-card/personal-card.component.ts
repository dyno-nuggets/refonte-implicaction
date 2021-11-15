import {Component, HostListener, Input} from '@angular/core';
import {User} from '../../../shared/models/user';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-personal-card',
  templateUrl: './personal-card.component.html',
  styleUrls: ['./personal-card.component.scss']
})
export class PersonalCardComponent {

  readonly YEAR_RANGE = `1900:${new Date().getFullYear() + 1}`;
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
      .updatePersonalInfo(this.user)
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
        this.userCopie.imageUrl = this.user.imageUrl;
        const formData = new FormData();
        formData.set('file', file, file.filename);
        this.userService
          .updateUserImage(formData)
          .subscribe(
            user => this.authService.setCurrentUser(user),
            () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la modification de votre image de profil')
          );
      };
    }
  }
}
