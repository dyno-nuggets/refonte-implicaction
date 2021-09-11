import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../../shared/models/user';
import {Constants} from '../../../config/constants';
import {ToasterService} from '../../../core/services/toaster.service';
import {RelationService} from '../../services/relation.service';
import {AuthService} from '../../../shared/services/auth.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  users: User[] = [];
  userId: string;
  friends: User[] = [];
  requestFriends: User[] = [];
  pendingFriends: User[] = [];

  // Pagination
  pageable = Constants.PAGEABLE_DEFAULT;
  rowsPerPage = 10;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private toastService: ToasterService,
    private relationService: RelationService
  ) {
  }

  ngOnInit(): void {
    this.userId = this.authService.getUserId();
    this.paginate({
      first: 0,
      rows: this.pageable.size,
    });
    this.relationService
      .getAllByUserId(this.userId)
      .subscribe(
        relations => {
          relations.forEach(relation => {
              if (relation.confirmedAt) {
                this.friends.push(relation.sender.id !== this.userId ? relation.sender : relation.receiver);
              } else if (relation.sender.id === this.userId) {
                this.requestFriends.push(relation.receiver);
              } else {
                this.pendingFriends.push(relation.sender);
              }
            },
            () => this.toastService.error('Oops', 'Une erreur est survenue lors du chargement de la liste des utilisateurs.'));
        }
      );
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

  isFriend = (user: User): boolean => this.friends.find(u => user.id === u.id) !== undefined;

  isFriendRequest = (user: User): boolean => this.requestFriends.find(u => user.id === u.id) !== undefined;

  isFriendPending = (user: User): boolean => this.pendingFriends.find(u => user.id === u.id) !== undefined;

  requestAsFriend(user: User): void {
    this.relationService
      .requestFriend(user.id)
      .subscribe(
        relation => this.requestFriends.push(relation.receiver),
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Ok', `Votre demande a bien été envoyée à ${user.nicename}`)
      );
  }
}
