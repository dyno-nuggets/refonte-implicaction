import {Component} from '@angular/core';
import {UserService} from '../../../../user/services/user.service';
import {finalize, take} from 'rxjs/operators';
import {ToasterService} from '../../../../core/services/toaster.service';
import {RoleEnum, RoleEnumCode} from '../../../../shared/enums/role.enum';
import {User} from '../../../../shared/models/user';
import {AuthService} from '../../../../shared/services/auth.service';
import {BaseWithPaginationAndFilterComponent} from '../../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {ActivatedRoute} from '@angular/router';
import {Criteria} from '../../../../shared/models/Criteria';

@Component({
  selector: 'app-pending-user-table',
  templateUrl: './pending-user-table.component.html',
  styleUrls: ['./pending-user-table.component.scss']
})
export class PendingUserTableComponent extends BaseWithPaginationAndFilterComponent<User, Criteria> {

  loading = true;
  rowsPerPage = this.pageable.rowsPerPages[0];

  constructor(
    private userService: UserService,
    private toastService: ToasterService,
    private authService: AuthService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  protected innerPaginate(): void {
    this.userService
      .getAllPendingActivationUsers(this.pageable)
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

  roleCodesToString(roles: RoleEnumCode[]): string {
    return roles ? roles.map(role => RoleEnum.from(role)?.label).join(', ') : '';
  }

  activateUser(user: User): void {
    this.authService
      .activateUser(user.activationKey)
      .subscribe(
        () => {
          this.paginate({first: this.pageable.first, rows: this.pageable.rows});
        },
        () => this.toastService.error('Oops', `Une erreur est survenue lors de la validation de l'utilisateur.`),
        () => this.toastService.success('Succès', `L'utilisateur ${user.username} est désormais actif.`),
      );
  }
}
