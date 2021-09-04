import {Component, OnInit} from '@angular/core';
import {UserService} from '../shared/services/user.service';
import {User} from '../shared/models/user';
import {Constants} from '../config/constants';
import {ToasterService} from '../core/services/toaster.service';

@Component({
  selector: 'app-community',
  templateUrl: './community.component.html',
  styleUrls: ['./community.component.scss']
})
export class CommunityComponent implements OnInit {

  users: User[] = [];

  // Pagination
  pageable = Constants.PAGEABLE_DEFAULT;
  rowsPerPage = 10;

  constructor(
    private userService: UserService,
    private toastService: ToasterService
  ) {
  }

  ngOnInit(): void {
    this.paginate({
      first: 0,
      rows: this.pageable.size,
    });
  }

  paginate({first, rows}): void {
    this.userService
      // TODO: ne récupérer que les utilisateurs dont le compte a été activé
      .getAll({page: first / rows, size: rows})
      .subscribe(
        userPageable => {
          this.pageable = userPageable;
          this.users = userPageable.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des utilisateurs')
      );
  }
}
