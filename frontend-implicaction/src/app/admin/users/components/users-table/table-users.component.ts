import {Component} from '@angular/core';
import {UserService} from '../../../../user/services/user.service';
import {ToasterService} from '../../../../core/services/toaster.service';
import {finalize, take} from 'rxjs/operators';
import {Pageable} from '../../../../shared/models/pageable';
import {Constants} from '../../../../config/constants';
import {User} from '../../../../shared/models/user';

@Component({
  selector: 'app-table-users',
  templateUrl: './table-users.component.html',
  styleUrls: ['./table-users.component.scss']
})
export class TableUsersComponent {

  loading = true; // indique si les données sont en chargement
  // Ne pas oublier de retirer du tableau la valeur 'all' utilisée
  // afin de pouvoir cocher/décocher toutes les cases d'un coup

  selectedUsers: User[] = [];

  // Pagination
  pageable: Pageable = Constants.PAGEABLE_DEFAULT;
  rowsPerPage = this.pageable.rowsPerPages[0];

  constructor(
    private userService: UserService,
    private toastService: ToasterService,
  ) {
  }

  loadUsers({first, rows}): void {
    this.loading = true;
    const page = first / rows;

    this.userService
      .getAll({page, rows})
      .pipe(
        take(1),
        finalize(() => this.loading = false)
      )
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.rows = data.size;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération des données'),
      );
  }
}
