import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../../shared/models/user';
import {Constants} from '../../../config/constants';
import {ToasterService} from '../../../core/services/toaster.service';
import {RelationService} from '../../services/relation.service';
import {AuthService} from '../../../shared/services/auth.service';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';

enum USER_LIST_TYPE {
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
  userId: string;
  friends: User[] = [];
  friendAsSenders: User[] = [];
  friendAsReceivers: User[] = [];
  action: string;
  listType: USER_LIST_TYPE;

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
    switch (router.url) {
      case USER_LIST_TYPE.FRIENDS:
        this.listType = USER_LIST_TYPE.FRIENDS;
        break;
      case USER_LIST_TYPE.FRIENDS_RECEIVED:
        this.listType = USER_LIST_TYPE.FRIENDS_RECEIVED;
        break;
      case USER_LIST_TYPE.FRIENDS_SENT:
        this.listType = USER_LIST_TYPE.FRIENDS_SENT;
        break;
      default:
        this.listType = USER_LIST_TYPE.ALL_USERS;
    }
  }

  ngOnInit(): void {
    this.userId = this.authService.getUserId();
    this.paginate({
      first: 0,
      rows: this.pageable.size,
    });
  }

  isFriend = (user: User): boolean => this.listType === USER_LIST_TYPE.FRIENDS || this.friends.find(u => user.id === u.id) !== undefined;

  // tslint:disable-next-line:max-line-length
  isSender = (user: User): boolean => this.listType === USER_LIST_TYPE.FRIENDS_SENT || this.friendAsSenders.find(u => user.id === u.id) !== undefined;

  // tslint:disable-next-line:max-line-length
  isReceiver = (user: User): boolean => this.listType === USER_LIST_TYPE.FRIENDS_RECEIVED || this.friendAsReceivers.find(u => user.id === u.id) !== undefined;

  paginate({first, rows}): void {
    this.pageable.page = first / rows;
    this.pageable.size = rows;
    let user$: Observable<any>;

    // on détermine quel observable à écouter en fonction dy type d'utilisateurs à afficher
    if (this.listType === USER_LIST_TYPE.ALL_USERS) {
      user$ = this.userService.getAll(this.pageable);
      this.getAllFriends();
    } else if (this.listType === USER_LIST_TYPE.FRIENDS) {
      user$ = this.userService.getUserFriends(this.userId, this.pageable);
    } else if (this.listType === USER_LIST_TYPE.FRIENDS_RECEIVED) {
      user$ = this.userService.getUserFriendRequestReceived(this.pageable);
    } else if (this.listType === USER_LIST_TYPE.FRIENDS_SENT) {
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
        relation => this.friendAsSenders.push(relation.receiver),
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Demande effectuée', `Votre demande a bien été envoyée à ${user.nicename}`)
      );
  }

  confirmUserAsFriend(sender: User): void {
    this.userService
      .confirmUserAsFriend(sender.id)
      .subscribe(
        relation => {
          if (this.listType === USER_LIST_TYPE.ALL_USERS) {
            this.friends.push(relation.sender);
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
          if (this.isFriend(user)) {
            if (this.listType === USER_LIST_TYPE.ALL_USERS) {
              this.friends = this.friends.filter(u => u.id !== user.id);
            }
            message = `L'utilisateur ${user.nicename} a bien été supprimé de vos amis`;
          } else if (this.isSender(user)) {
            if (this.listType === USER_LIST_TYPE.ALL_USERS) {
              this.friendAsSenders = this.friendAsSenders.filter(u => u.id !== user.id);
            }
            message = `Vous avez annulé la demande d'ami avec ${user.nicename}`;
          } else if (this.isReceiver(user)) {
            if (this.listType === USER_LIST_TYPE.ALL_USERS) {
              this.friendAsReceivers = this.friendAsReceivers.filter(u => u.id !== user.id);
            }
            message = `Vous avez refusé la demande d'ami de ${user.nicename}`;
          }
          // il faut relancer la pagination dans le cas de l'affichage des amis / demandes
          if (this.listType !== USER_LIST_TYPE.ALL_USERS) {
            const first = this.pageable.page * this.pageable.size;
            const rows = this.pageable.size;
            this.paginate({first, rows});
          }
        },
        () => this.toastService.error('Erreur', 'Une erreur est survenue'),
        () => this.toastService.success('Succès', message)
      );
  }

  private getAllFriends(): void {
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
}
