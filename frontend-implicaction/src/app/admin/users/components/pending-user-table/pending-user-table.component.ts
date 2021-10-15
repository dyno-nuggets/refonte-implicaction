import {Component} from '@angular/core';
import {UserService} from '../../../../user/services/user.service';
import {Constants} from '../../../../config/constants';
import {LazyLoadEvent} from 'primeng/api';
import {finalize, take} from 'rxjs/operators';
import {ToasterService} from '../../../../core/services/toaster.service';
import {RoleEnum, RoleEnumCode} from '../../../../shared/enums/role.enum';
import {User} from '../../../../shared/models/user';
import {AuthService} from '../../../../shared/services/auth.service';

@Component({
  selector: 'app-pending-user-table',
  templateUrl: './pending-user-table.component.html',
  styleUrls: ['./pending-user-table.component.scss']
})
export class PendingUserTableComponent {

  loading = true;
  pageable = Constants.PAGEABLE_DEFAULT;
  rowsPerPage = this.pageable.rowsPerPages[0];

  constructor(
    private userService: UserService,
    private toastService: ToasterService,
    private authService: AuthService
  ) {
  }

  loadUsers(event: LazyLoadEvent): void {
    this.loading = true;
    const page = event.first / event.size;

    this.userService
      .getAllPendingActivationUsers({page, size: event.size})
      .pipe(
        take(1),
        finalize(() => this.loading = false)
      )
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.size = data.size;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération des données'),
      );
  }

  roleCodesToString(roles: RoleEnumCode[]): string {
    return roles ? roles.map(role => RoleEnum.from(role)?.label).join(', ') : '';
  }

  activateUser(user: User, event): void {
    console.log(event);
    this.authService
      .activateUser(user.activationKey)
      .subscribe(
        () => {
          const first = this.pageable.page * this.pageable.size;
          const rows = this.pageable.size;
          this.loadUsers({first, rows: size});
        },
        () => this.toastService.error('Oops', `Une erreur est survenue lors de la validation de l'utilisateur.`),
        () => this.toastService.success('Succès', `L'utilisateur ${user.username} est désormais actif.`),
      );
  }
}
