import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../../shared/models/user';
import {Constants} from '../../../config/constants';
import {ToasterService} from '../../../core/services/toaster.service';
import {RelationService} from '../../services/relation.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ActivatedRoute, NavigationEnd, NavigationError, NavigationStart, Router} from '@angular/router';

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

  // Pagination
  pageable = Constants.PAGEABLE_DEFAULT;
  rowsPerPage = 10;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private toastService: ToasterService,
    private relationService: RelationService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.userId = this.authService.getUserId();

    // this.route.paramMap.subscribe(paramMap => {
    //   this.action = paramMap.get('action');
    //   switch (this.action) {
    //     case 'list':
    //       this.getUsers();
    //       break;
    //     case 'friends':
    //       this.getFriends();
    //       break;
    //     case 'received':
    //     case 'sent':
    //       this.getFriendRequests(this.action);
    //       break;
    //   }
    // });

    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        // Show progress spinner or progress bar
        console.log('Route change detected');
      }

      if (event instanceof NavigationEnd) {
        // charger le tableau de type
        const url = event.url;
        console.log(url);

        if (url === '/users/list') {
          // this.getUsers();
          this.paginate({
            first: 0,
            rows: this.pageable.size,
          });
        }
      }

      if (event instanceof NavigationError) {
        // Hide progress spinner or progress bar

        // Present error to user
        console.log(event.error);
      }
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

  private getUsers(): void {
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

  private getFriends(): void {
    this.users = [];
    // this.relationService
    //   .getFriends(this.userId)
    //   .subscribe(friends => this.users = friends);
  }

  private getFriendRequests(type: string): void {
    this.users = [];
    // this.relationService
    //   .getReceivedRequests(this.userId)
    //   .subscribe(senders => this.users = senders);
  }
}
