import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {User} from "../../../../shared/models/user";
import {RoleEnum} from "../../../../shared/enums/role.enum";
import {UserService} from "../../../../community/services/profile/user.service";
import {PaginationUpdate} from "../../../../shared/models/pagination-update";

@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UsersTableComponent implements OnChanges {

  @Input() users: User[];
  @Input() rowsPerPages: number[];
  @Input() rows: number
  @Input() page: number;
  @Input() totalPage: number;
  @Input() title: string;
  @Input() loading = false;

  @Output() paginationChange = new EventEmitter<PaginationUpdate>();
  @Output() userUpdate = new EventEmitter<User>();
  @Output() sortChange = new EventEmitter<string>();

  roles = RoleEnum.all();


  constructor(private userService: UserService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.hasOwnProperty('pageable')) {
      this.users = [...changes['pageable'].currentValue.content];
    }
  }

  disableUser(user: User): void {
    throw new Error('Method not implemented.');
  }

  enableUser(user: User): void {
    this.userService.enableUser(user.username)
      .subscribe(userEnabled => this.userUpdate.emit(userEnabled));
  }

  saveUserRoles(user): void {
    this.userService.updateUserRoles(user.username, user.roles)
      .subscribe(userSaved => this.userUpdate.emit(userSaved));
  }

  updateRowsPerPage(rowsPerPage: number): void {
    this.paginationChange.emit({rowsPerPage});
  }

  updatePage(page: number): void {
    this.paginationChange.emit({page});
  }

  updateSort(key: string): void {
    this.sortChange.emit(key);
  }
}
