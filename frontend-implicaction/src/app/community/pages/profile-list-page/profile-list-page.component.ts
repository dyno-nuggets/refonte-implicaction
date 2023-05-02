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
import {RelationCriteriaEnum} from '../../models/enums/relation-criteria-enum';
import {RelationButton} from '../../models/relation-button';

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
  relationTypeCriteria: RelationCriteriaEnum;
  menuItems: MenuItem[] = [
    {
      label: 'Tous les utilisateurs',
      routerLink: `/${Univers.COMMUNITY.url}/profiles`,
      routerLinkActiveOptions: {exact: true}
    },
    {
      label: 'Mes amis',
      routerLink: `/${Univers.COMMUNITY.url}/profiles`,
      queryParams: {filter: RelationCriteriaEnum.ALL_FRIENDS},
      routerLinkActiveOptions: {exact: true}
    },
    {
      label: 'Invitations',
      routerLink: `/${Univers.COMMUNITY.url}/profiles`,
      queryParams: {filter: RelationCriteriaEnum.ONLY_FRIEND_REQUESTS},
      routerLinkActiveOptions: {exact: true}
    }
  ];

  private onDestroySubject = new Subject<void>();

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
    this.route.queryParams
      .pipe(takeUntil(this.onDestroySubject))
      .subscribe(params => {
        this.pageable.number = (params.page ?? 1) - 1;
        this.relationTypeCriteria = params.filter ?? RelationCriteriaEnum.ANY;
        this.fetchProfiles();
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
  }

  private fetchProfiles(): void {
    this.loading = true;
    this.profileService.getAllProfiles(this.relationTypeCriteria ?? RelationCriteriaEnum.ANY, this.pageable)
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
