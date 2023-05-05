import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from '../../../shared/models/user';
import {ToasterService} from '../../../core/services/toaster.service';
import {RelationService} from '../../services/relation.service';
import {AuthService} from '../../../core/services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {finalize, take, takeUntil} from 'rxjs/operators';
import {Univers} from '../../../shared/enums/univers';
import {ProfileService} from '../../services/profile/profile.service';
import {MenuItem} from 'primeng/api';
import {Pageable} from '../../../shared/models/pageable';
import {Profile} from '../../models/profile';
import {Constants} from '../../../config/constants';
import {RelationActionEnumCode} from '../../models/enums/relation-action';
import {Relation} from '../../models/relation';
import {Subject} from 'rxjs';
import {RelationButton} from '../../models/relation-button';
import {ProfileMenuCode, ProfileMenuEnum} from "../../models/enums/profile-menu-enum";

@Component({
  templateUrl: './profile-list-page.component.html',
  styleUrls: ['./profile-list-page.component.scss']
})
export class ProfileListPageComponent implements OnInit, OnDestroy {

  readonly univers = Univers;

  pageable: Pageable<Profile> = {...Constants.PAGEABLE_DEFAULT, sortBy: 'user.lastname,user.firstname'};
  profiles: Profile[] = [];
  currentUser: User;
  action: string;
  loading = true;
  selectedItemMenuCode: ProfileMenuCode;
  menuItems: MenuItem[] = ProfileMenuEnum.values()
    .map((item: ProfileMenuEnum) => ({
      id: item.code,
      label: item.label,
      routerLink: item.url,
      queryParams: {filter: item.code},
      routerLinkActiveOptions: {queryParams: 'subset'}
    } as MenuItem));

  private onDestroySubject = new Subject<void>();
  private onlyFriendRequestMenuItem: MenuItem;

  constructor(
    private authService: AuthService,
    private profileService: ProfileService,
    private toastService: ToasterService,
    private relationService: RelationService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.currentUser = this.authService.getPrincipal();
    this.onlyFriendRequestMenuItem = this.menuItems.find(item => item.id === ProfileMenuCode.ONLY_FRIEND_REQUESTS);
    this.route.queryParams
      .pipe(takeUntil(this.onDestroySubject))
      .subscribe(params => {
        this.pageable.number = (params.page ?? 1) - 1;
        this.selectedItemMenuCode = params.filter ?? ProfileMenuCode.ALL;
        this.fetchProfiles();
      });

    this.profileService.getProfileRequestAsFriendCount().subscribe(relationCount => {
      if (this.onlyFriendRequestMenuItem) {
        this.onlyFriendRequestMenuItem.badge = relationCount ? `${relationCount}` : null;
      }
    });
  }

  trackByUsername = (index: number, item: Profile) => item.username;

  setCurrentPage(page: number): void {
    this.router.navigate(
      ['/community/profiles'],
      {
        queryParams: {page: page + 1},
        queryParamsHandling: 'merge'
      }
    );
  }

  updateRelation(profile: Profile, relationAction: RelationButton): void {
    switch (relationAction.action.code) {
      case RelationActionEnumCode.CONFIRM:
        this.relationService.confirmRelation(relationAction.relation.id)
          .subscribe(relation => {
            this.updateProfileRelation(profile, relation);
            this.toastService.success('Succès', 'la relation a bien été confirmée');
          });
        break;

      case RelationActionEnumCode.CREATE:
        this.relationService.createRelation({
          sender: relationAction.relation.sender.username,
          receiver: relationAction.relation.receiver.username
        }).subscribe(relation => {
          this.updateProfileRelation(profile, relation);
          this.toastService.success('Succès', 'la relation a bien été demandée');
        });
        break;

      case RelationActionEnumCode.DELETE:
        this.relationService.deleteRelation(relationAction.relation.id)
          .subscribe(() => {
            this.updateProfileRelation(profile, null);
            this.toastService.success('Succès', 'la relation a bien été supprimée');
          });
        break;

      default:
        this.toastService.error('Oops', 'action non implémentée');
        break;
    }
  }

  /**
   * Met à jour la relation liée au profil
   * @param profile le profil à mettre à jour
   * @param relationWithCurrentUser la nouvelle relation (peut être null)
   * @private
   */
  private updateProfileRelation(profile: Profile, relationWithCurrentUser: Relation): void {
    const index = this.pageable.content.findIndex(p => p.username === profile.username);
    this.pageable.content.splice(index, 1, {...profile, relationWithCurrentUser});
    this.profiles = [...this.pageable.content];
    if (relationWithCurrentUser.confirmedAt) {
      const relationCount = +this.onlyFriendRequestMenuItem.badge - 1;
      this.onlyFriendRequestMenuItem.badge = relationCount ? `${relationCount}` : null;
    }
  }

  private fetchProfiles(): void {
    this.loading = true;
    this.profileService.getAllProfiles(this.selectedItemMenuCode ?? ProfileMenuCode.ALL, this.pageable)
      .pipe(
        finalize(() => this.loading = false),
        take(1),
      )
      .subscribe({
        next: pageable => {
          this.pageable = {...this.pageable, ...pageable};
          this.profiles = [...this.pageable.content];
        },
        error: () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des utilisateurs')
      });
  }

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }
}
