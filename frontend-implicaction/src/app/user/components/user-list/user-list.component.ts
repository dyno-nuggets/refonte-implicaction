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
  friendAsSenders: User[] = [];
  friendAsReceivers: User[] = [];

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
      // TODO: peut être optimisé en ne recherchant que les relations avec les utilisateurs affichés
      .getAllByUserId(this.userId)
      .subscribe(
        relations => {
          relations.forEach(relation => {
              if (relation.confirmedAt) {
                this.friends.push(relation.sender.id !== this.userId ? relation.sender : relation.receiver);
              } else if (relation.sender.id === this.userId) {
                this.friendAsSenders.push(relation.receiver);
              } else {
                this.friendAsReceivers.push(relation.sender);
              }
            },
            () => this.toastService.error('Oops', 'Une erreur est survenue lors du chargement de la liste des utilisateurs.'));
        });
  }

  isFriend = (user: User): boolean => this.friends.find(u => user.id === u.id) !== undefined;

  isSender = (user: User): boolean => this.friendAsSenders.find(u => user.id === u.id) !== undefined;

  isReceiver = (user: User): boolean => this.friendAsReceivers.find(u => user.id === u.id) !== undefined;

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

  requestAsFriend(user: User): void {
    this.relationService
      .requestFriend(user.id)
      .subscribe(
        relation => this.friendAsSenders.push(relation.receiver),
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Demande effectuée', `Votre demande a bien été envoyée à ${user.nicename}`)
      );
  }
}
