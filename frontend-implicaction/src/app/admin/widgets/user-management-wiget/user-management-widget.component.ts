import {Component, Input, OnInit} from '@angular/core';
import {Pageable} from "../../../shared/models/pageable";
import {User} from "../../../shared/models/user";
import {Constants} from "../../../config/constants";
import {UserService} from "../../../community/services/profile/user.service";
import {ToasterService} from "../../../core/services/toaster.service";
import {finalize} from "rxjs/operators";
import {PaginationUpdate} from "../../../shared/models/pagination-update";

@Component({
  selector: 'app-user-management-widget',
  templateUrl: './user-management-widget.component.html',
})
export class UserManagementWidgetComponent implements OnInit {

  @Input() title;
  @Input() rows = 25;
  @Input() rowsPerPages;
  @Input() sortBy: 'id' | 'lastname,firstname' | 'registeredAt' | 'enabled' = 'id';
  @Input() sortOrder: 'ASC' | 'DESC' = 'ASC';

  pageable: Pageable<User>;
  loading = true;

  constructor(
    private userService: UserService,
    private toasterService: ToasterService
  ) {
  }

  ngOnInit(): void {
    this.pageable = {...Constants.PAGEABLE_DEFAULT, rows: this.rows, sortBy: this.sortBy, sortOrder: this.sortOrder, rowsPerPages: this.rowsPerPages}
    this.fetchUsers();
  }

  updateUser(userUpdated: User): void {
    const index = this.pageable.content.findIndex(user => userUpdated.id === user.id);
    this.pageable.content.splice(index, 1, userUpdated)
    this.pageable = {...this.pageable};
    this.toasterService.success('Succès', 'L\'utilisateur a bien été mis à jour')
  }

  updatePagination(update: PaginationUpdate) {
    if (update.rowsPerPage) {
      this.pageable.rows = update.rowsPerPage;
    }
    if (update.page !== undefined) {
      this.pageable.number = update.page;
    }
    this.fetchUsers();
  }

  private fetchUsers(pageable = this.pageable): void {
    this.userService.getAll(pageable)
      .pipe(finalize(() => this.loading = false))
      .subscribe(pageable => this.pageable = {...this.pageable, ...pageable});
  }

}
