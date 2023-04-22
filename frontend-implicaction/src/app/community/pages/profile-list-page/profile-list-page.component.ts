import {Component, OnInit} from '@angular/core';
import {User} from '../../../shared/models/user';
import {ToasterService} from '../../../core/services/toaster.service';
import {RelationService} from '../../services/relation.service';
import {AuthService} from '../../../core/services/auth.service';
import {ActivatedRoute} from '@angular/router';
import {RelationType} from '../../models/relation-type.enum';
import {finalize, tap} from 'rxjs/operators';
import {Univers} from '../../../shared/enums/univers';
import {BaseWithPaginationAndFilterComponent} from '../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {Criteria} from '../../../shared/models/Criteria';
import {ProfileService} from "../../services/profile/profile.service";
import {Relation} from "../../models/relation";
import {Profile} from "../../models/profile/profile";
import {MenuItem} from "primeng/api";

@Component({
  templateUrl: './profile-list-page.component.html',
  styleUrls: ['./profile-list-page.component.scss']
})
export class ProfileListPageComponent extends BaseWithPaginationAndFilterComponent<Profile, Criteria> implements OnInit {

  readonly univers = Univers;

  currentUser: User;
  action: string;
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
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  ngOnInit(): void {
    this.currentUser = this.authService.getPrincipal();
    this.paginate();
  }

  protected innerPaginate(): void {
    this.profileService.getAllProfiles(this.pageable)
      .pipe(finalize(() => this.isLoading = false), tap(console.log))
      .subscribe({
        next: pageable => this.pageable = pageable,
        error: () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des utilisateurs')
      });
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
}
