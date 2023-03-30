import {Component, OnInit} from '@angular/core';
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
import {ProfileService} from "../../../profile/services/profile.service";
import {Relation} from "../../models/relation";

enum UserListType {
  ALL_USERS = 'community',
  FRIENDS = 'friends',
  FRIENDS_RECEIVED = 'received',
  FRIENDS_SENT = 'sent'
}

@Component({
  selector: 'app-user-list',
  templateUrl: './relation-list.component.html',
  styleUrls: ['./relation-list.component.scss']
})
export class RelationListComponent extends BaseWithPaginationAndFilterComponent<Relation, Criteria> implements OnInit {

  readonly univers = Univers;

  user$: Observable<any>
  currentUser: User;
  action: string;
  listType: UserListType;
  relationType = RelationType;
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
    private profileService: ProfileService,
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

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    // on détermine quel est l’observable auquel s’abonner en fonction du type d’utilisateurs à afficher
    this.route.url.subscribe(urlSegments => {
      if (urlSegments.length === 2 && urlSegments[1].path === UserListType.FRIENDS_RECEIVED) {
        this.listType = UserListType.FRIENDS_RECEIVED;
      } else if (urlSegments.length === 2 && urlSegments[1].path === UserListType.FRIENDS_SENT) {
        this.listType = UserListType.FRIENDS_SENT;
      } else if (urlSegments.length === 1) {
        this.listType = UserListType.FRIENDS;
      } else {
        this.listType = UserListType.ALL_USERS;
      }
    });
    // chargement des données
    this.paginate();
  }

  protected innerPaginate(): void {
    switch (this.listType) {
      case UserListType.FRIENDS:
        this.user$ = this.relationService.getAllRelationsByUsername(this.currentUser?.username, this.pageable);
        break;
      case UserListType.FRIENDS_RECEIVED:
        this.user$ = this.relationService.getAllRelationRequestsReceived(this.currentUser?.username, this.pageable);
        break;
      case UserListType.FRIENDS_SENT:
        this.user$ = this.relationService.getAllRelationRequestSent(this.currentUser?.username, this.pageable);
        break;
      default:
        this.user$ = this.relationService.getAllCommunity(this.pageable);
    }
    this.user$.pipe(finalize(() => this.isLoading = false))
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

  requestUserAsFriend(relation: Relation): void {
    this.relationService
      .requestRelation(relation.receiver.username)
      .subscribe(
        r => {
          relation.relationType = RelationType.SENDER;
          relation.id = r.id;
          relation.sender = r.sender;
          relation.receiver = r.receiver;
          relation.sentAt = r.sentAt;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Demande effectuée', `Votre demande a bien été envoyée à ${relation.receiver.username}`)
      );
  }

  confirmRelation(relation: Relation): void {
    this.relationService
      .confirmRelation(relation.id)
      .subscribe(
        () => {
          if (this.listType === UserListType.ALL_USERS) {
            relation.relationType = RelationType.FRIEND;
          } else {
            this.paginate();
          }
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue'),
        () => this.toastService.success('Demande acceptée', `Vous avez accepté la demande d'ami`)
      );
  }

  /**
   * permet de refuser, annuler une demande d’ami ou de supprimer un ami
   */
  deleteRelation(relation: Relation): void {
    let message = '';
    this.relationService
      .removeRelation(relation.id)
      .subscribe(
        () => {
          if (relation.relationType === RelationType.FRIEND) {
            message = `L'utilisateur a bien été supprimé de vos relations`;
          } else if (relation.relationType === RelationType.SENDER) {
            message = `Vous avez annulé la demande de mise en relation`;
          } else if (relation.relationType === RelationType.RECEIVER) {
            message = `Vous avez refusé la demande de mise en relation`;
          }

          relation.receiver = relation.receiver.username === this.currentUser.username ? relation.sender : relation.receiver;
          relation.relationType = RelationType.NONE;
          relation.id = null;
          relation.sentAt = null;
          relation.confirmedAt = null;

          // il faut relancer la pagination dans le cas de l’affichage des amis / demandes
          if (this.listType !== UserListType.ALL_USERS) {
            this.paginate();
          }
        },
        () => this.toastService.error('Erreur', 'Une erreur est survenue'),
        () => this.toastService.success('Succès', message)
      );
  }
}
