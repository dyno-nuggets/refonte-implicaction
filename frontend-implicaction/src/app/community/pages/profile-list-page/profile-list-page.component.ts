import {Component, OnInit} from '@angular/core';
import {User} from '../../../shared/models/user';
import {ToasterService} from '../../../core/services/toaster.service';
import {RelationService} from '../../services/relation.service';
import {AuthService} from '../../../core/services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {RelationTypeCode} from '../../models/relation-type.enum';
import {finalize, take, tap} from 'rxjs/operators';
import {Univers} from '../../../shared/enums/univers';
import {ProfileService} from "../../services/profile/profile.service";
import {MenuItem} from "primeng/api";
import {Pageable} from "../../../shared/models/pageable";
import {Profile} from "../../models/profile/profile";
import {Constants} from "../../../config/constants";

@Component({
  templateUrl: './profile-list-page.component.html',
  styleUrls: ['./profile-list-page.component.scss']
})
export class ProfileListPageComponent implements OnInit {

  readonly univer = Univers;
  readonly relationTypeCode = RelationTypeCode
  pageable: Pageable<Profile> = Constants.PAGEABLE_DEFAULT;
  currentUser: User;
  action: string;
  relationType = RelationTypeCode;
  menuItems: MenuItem[] = [
    {
      label: 'Tous les utilisateurs',
      routerLink: `/${Univers.COMMUNITY.url}/profiles`
    },
    {
      label: 'Mes amis',
      routerLink: `/${Univers.COMMUNITY.url}/relations`
    },
    {
      label: 'Invitations',
      routerLink: `/${Univers.COMMUNITY.url}/relations/received`
    }
  ];

  loading = true;

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
    this.route.queryParams.subscribe(params => {
      this.pageable.number = params.page ? (params.page - 1) : Constants.PAGEABLE_DEFAULT.number;
      this.fetchProfiles();
    });
  }

  setCurrentPage(page: number): void {
    this.router.navigate(
      ['/community/profiles'],
      {
        queryParams: {page: page + 1},
        queryParamsHandling: 'merge'
      }
    );
  }

  private fetchProfiles(): void {
    this.loading = true;
    this.profileService.getAllProfiles(this.pageable)
      .pipe(finalize(() => this.loading = false), take(1), tap(console.log))
      .subscribe({
        next: pageable => this.pageable = pageable,
        error: () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des utilisateurs')
      });
  }
}
