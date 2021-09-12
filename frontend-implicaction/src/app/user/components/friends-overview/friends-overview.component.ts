import {Component, OnInit} from '@angular/core';
import {User} from '../../../shared/models/user';
import {Constants} from '../../../config/constants';
import {UserService} from '../../services/user.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-friends-overview',
  templateUrl: './friends-overview.component.html',
  styleUrls: ['./friends-overview.component.scss']
})
export class FriendsOverviewComponent implements OnInit {

  users: User[] = [];
  userId: string;

  // Pagination
  pageable = Constants.PAGEABLE_DEFAULT;
  rowsPerPage = 10;

  constructor(
    private userService: UserService,
    private toastService: ToasterService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      const userId = paramMap.get('userId');
      this.userService
        .getUserById(userId)
        .subscribe(
          user => this.userId = user.id,
          () => this.toastService.error('oops', 'Une erreur est survenue !')
        );
    });
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
