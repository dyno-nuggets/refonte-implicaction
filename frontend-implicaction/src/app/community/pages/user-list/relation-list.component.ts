import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../profile/services/user.service';
import {User} from '../../../shared/models/user';
import {ToasterService} from '../../../core/services/toaster.service';
import {RelationService} from '../../services/relation.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {RelationType} from '../../models/relation-type.enum';
import {finalize} from 'rxjs/operators';
import {Univers} from '../../../shared/enums/univers';
import {MenuItem} from 'primeng/api';
import {
  BaseWithPaginationAndFilterComponent
} from '../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {Criteria} from '../../../shared/models/Criteria';

enum UserListType {
  ALL_USERS = '/community',
  FRIENDS = '/community/friends',
  FRIENDS_RECEIVED = 'received',
  FRIENDS_SENT = '/community/sent'
}

@Component({
  selector: 'app-user-list',
  templateUrl: './relation-list.component.html',
  styleUrls: ['./relation-list.component.scss']
})
export class RelationListComponent extends BaseWithPaginationAndFilterComponent<User, Criteria> implements OnInit {

  readonly univer = Univers;

  currentUser: User;
  action: string;
  listType: UserListType;
  relationType = RelationType;
  isLoading = true;
  univers = Univers;
  menuItems: MenuItem[] = [
    {
      label: 'Tous les utilisateurs',
      routerLink: `/${Univers.COMMUNITY.url}`
    },
    {
      label: 'Mes amis',
      routerLink: `/${Univers.COMMUNITY.url}/relations`
    },
    {
      label: 'Demandes reçues',
      routerLink: `/${Univers.COMMUNITY.url}/relations/received`
    },
    {
      label: 'Demandes envoyées',
      routerLink: `/${Univers.COMMUNITY.url}/relations/sent`
    }
  ];

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private toastService: ToasterService,
    private relationService: RelationService,
    private router: Router,
    protected route: ActivatedRoute
  ) {
    super(route);
    // on détermine le type de données à afficher en fonction de l’url
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
    this.currentUser = this.authService.getCurrentUser();
    // chargement des données
    this.paginate();
  }

  protected innerPaginate(): void {
    let user$: Observable<any>;

    // on détermine quel est l'observable auquel s'abonner en fonction du type d'utilisateurs à afficher
    if (this.listType === UserListType.ALL_USERS) {
      user$ = this.userService.getAllCommunity(this.pageable);
    } else if (this.listType === UserListType.FRIENDS) {
      user$ = this.relationService.getAllRelationsByUserId(this.currentUser?.id, this.pageable);
    } else if (this.listType === UserListType.FRIENDS_RECEIVED) {
      user$ = this.relationService.getAllRelationRequestsReceived(this.pageable);
    } else if (this.listType === UserListType.FRIENDS_SENT) {
      user$ = this.relationService.getAllRelationRequestSent(this.pageable);
    }

    user$.pipe(finalize(() => this.isLoading = false))
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.rows = data.size;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
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
        () => this.toastService.success('Demande effectuée', `Votre demande a bien été envoyée à ${user.firstname}`)
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
            this.paginate();
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
          if (RelationListComponent.isFriend(user)) {
            message = `L'utilisateur ${user.firstname} a bien été supprimé de vos amis`;
          } else if (RelationListComponent.isSender(user)) {
            message = `Vous avez annulé la demande d'ami avec ${user.firstname}`;
          } else if (RelationListComponent.isReceiver(user)) {
            message = `Vous avez refusé la demande d'ami de ${user.firstname}`;
          }
          user.relationTypeOfCurrentUser = RelationType.NONE;
          // il faut relancer la pagination dans le cas de l'affichage des amis / demandes
          if (this.listType !== UserListType.ALL_USERS) {
            this.paginate();
          }
        },
        () => this.toastService.error('Erreur', 'Une erreur est survenue'),
        () => this.toastService.success('Succès', message)
      );
  }
}
