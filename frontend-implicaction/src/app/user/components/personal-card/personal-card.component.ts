import {Component, Input, OnInit} from '@angular/core';
import {User} from '../../../shared/models/user';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {finalize} from 'rxjs/operators';

@Component({
  selector: 'app-personal-card',
  templateUrl: './personal-card.component.html',
  styleUrls: ['./personal-card.component.scss']
})
export class PersonalCardComponent implements OnInit {

  @Input()
  user: User;
  userCopies: User;
  currentUserId: string;
  isEditing = false;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private toasterService: ToasterService
  ) {
  }

  ngOnInit(): void {
    this.currentUserId = this.authService.getUserId();
  }

  toggleModeEdition(): void {
    if (!this.isEditing) {
      // on clone le user afin de pouvoir rollback
      this.userCopies = {...this.user};
    } else {
      this.user = {...this.userCopies};
    }
    this.isEditing = !this.isEditing;
  }

  updatePersonalInfo(): void {
    this.userService
      .updatePersonalInfo(this.currentUserId, this.user)
      .pipe(finalize(() => this.isEditing = false))
      .subscribe(
        personalInfoUpdates => this.user = {...personalInfoUpdates},
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données'),
        () => this.toasterService.success('Ok', 'Le changement des données a bien été effectué')
      );
  }
}
