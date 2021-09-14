import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../../shared/models/user';
import {Constants} from '../../../config/constants';
import {ToasterService} from '../../../core/services/toaster.service';
import {RelationService} from '../../services/relation.service';
import {AuthService} from '../../../shared/services/auth.service';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {RelationType} from '../../models/relation-type.enum';

enum UserListType {
  ALL_USERS = '/users/list',
  FRIENDS = '/users/friends',
  FRIENDS_RECEIVED = '/users/friends/received',
  FRIENDS_SENT = '/users/friends/sent'
}

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  users: User[] = [];
  currentUserId: string;
  action: string;
  listType: UserListType;
  relationType = RelationType;

  // Pagination
  pageable = Constants.PAGEABLE_DEFAULT;
  rowsPerPage = 10;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private toastService: ToasterService,
    private relationService: RelationService,
    private router: Router,
  ) {
    // on détermine le type de données à afficher en fonction de l'url
    switch (router.url) {
      case UserListType.FRIENDS:
        this.listType = UserListType.FRIENDS;
        break;
      case UserListType.FRIENDS_RECEIVED:
        this.listType = UserListType.FRIENDS_RECEIVED;
        break;
      case UserListType.FRIENDS_SENT:
        this.listType = UserListType.FRIENDS_SENT;
        break;
      default:
        this.listType = UserListType.ALL_USERS;
    }
  }

  private static isFriend = (user: User): boolean => user.relationTypeOfCurrentUser === RelationType.FRIEND;

  private static isSender = (user: User): boolean => user.relationTypeOfCurrentUser === RelationType.SENDER;

  private static isReceiver = (user: User): boolean => user.relationTypeOfCurrentUser === RelationType.RECEIVER;

  ngOnInit(): void {
    this.currentUserId = this.authService.getUserId();
    // chargement des données
    this.paginate({
      first: 0,
      rows: this.pageable.size,
    });
  }

  paginate({first, rows}): void {
    this.pageable.page = first / rows;
    this.pageable.size = rows;
    let user$: Observable<any>;

    // on détermine quel est l'observable auquel s'abonner en fonction du type d'utilisateurs à afficher
    if (this.listType === UserListType.ALL_USERS) {
      user$ = this.userService.getAll(this.pageable);
    } else if (this.listType === UserListType.FRIENDS) {
      user$ = this.userService.getUserFriends(this.currentUserId, this.pageable);
    } else if (this.listType === UserListType.FRIENDS_RECEIVED) {
      user$ = this.userService.getUserFriendRequestReceived(this.pageable);
    } else if (this.listType === UserListType.FRIENDS_SENT) {
      user$ = this.userService.getUserFriendRequestSent(this.pageable);
    }

    user$.subscribe(
      data => {
        this.pageable.page = data.pageable.pageNumber;
        this.pageable.size = data.pageable.pageSize;
        this.users = data.content;
      },
      () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des utilisateurs')
    );
  }

  requestUserAsFriend(user: User): void {
    this.relationService
      .requestFriend(user.id)
      .subscribe(
        () => user.relationTypeOfCurrentUser = RelationType.SENDER,
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Demande effectuée', `Votre demande a bien été envoyée à ${user.nicename}`)
      );
  }

  confirmUserAsFriend(sender: User): void {
    this.userService
      .confirmUserAsFriend(sender.id)
      .subscribe(
        () => {
          if (this.listType === UserListType.ALL_USERS) {
            sender.relationTypeOfCurrentUser = RelationType.FRIEND;
          } else {
            const first = this.pageable.page * this.pageable.size;
            const rows = this.pageable.size;
            this.paginate({first, rows});
          }
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Demande acceptée', `Vous avez accepté la demande d'ami`)
      );
  }

  /**
   * permet de refuser, annuler une demande d'ami ou de supprimer un ami
   */
  removeUserRelation(user: User): void {
    let message = '';
    this.userService
      .removeUserFromFriends(user.id)
      .subscribe(
        () => {
          if (UserListComponent.isFriend(user)) {
            message = `L'utilisateur ${user.nicename} a bien été supprimé de vos amis`;
          } else if (UserListComponent.isSender(user)) {
            message = `Vous avez annulé la demande d'ami avec ${user.nicename}`;
          } else if (UserListComponent.isReceiver(user)) {
            message = `Vous avez refusé la demande d'ami de ${user.nicename}`;
          }
          user.relationTypeOfCurrentUser = RelationType.NONE;
          // il faut relancer la pagination dans le cas de l'affichage des amis / demandes
          if (this.listType !== UserListType.ALL_USERS) {
            const first = this.pageable.page * this.pageable.size;
            const rows = this.pageable.size;
            this.paginate({first, rows});
          }
        },
        () => this.toastService.error('Erreur', 'Une erreur est survenue'),
        () => this.toastService.success('Succès', message)
      );
  }
}
