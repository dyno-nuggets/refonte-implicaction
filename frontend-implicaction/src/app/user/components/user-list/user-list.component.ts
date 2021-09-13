import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../../shared/models/user';
import {Constants} from '../../../config/constants';
import {ToasterService} from '../../../core/services/toaster.service';
import {RelationService} from '../../services/relation.service';
import {AuthService} from '../../../shared/services/auth.service';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {Pageable} from '../../../shared/models/pageable';

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
    let user$: Observable<Pageable<User>>;

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
