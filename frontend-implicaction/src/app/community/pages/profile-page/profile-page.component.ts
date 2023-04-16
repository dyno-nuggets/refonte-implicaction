import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ProfileService} from '../../services/profile/profile.service';
import {Profile} from '../../models/profile/profile';
import {Univers} from '../../../shared/enums/univers';
import {Observable, Subject} from 'rxjs';
import {ProfileTabEnum} from '../../models/profile/enums/profile-tab-enum';
import {filter, takeUntil} from 'rxjs/operators';
import {AuthService} from '../../../core/services/auth.service';
import {ProfileContextService} from '../../../core/services/profile-context.service';

@Component({
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss'],
})
export class ProfilePageComponent implements OnInit, OnDestroy {
  profile$: Observable<Profile>;
  univers = Univers;
  activeTab: ProfileTabEnum = ProfileTabEnum.PROFILE;
  tabEnum = ProfileTabEnum;
  username: string;
  isPrincipal = false;

  private static readonly TAB_KEY_QUERY_PARAM = 'tab';
  private static readonly USER_PARAM_NAME = 'username';
  private onDestroySubject = new Subject<void>();

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private profileService: ProfileService,
    private authService: AuthService,
    private profileContextService: ProfileContextService
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap
      .pipe(filter(paramMap => paramMap.has(ProfilePageComponent.USER_PARAM_NAME)), takeUntil(this.onDestroySubject))
      .subscribe(paramMap => {
        this.username = paramMap.get(ProfilePageComponent.USER_PARAM_NAME);
        this.isPrincipal = this.username === this.authService.getPrincipal()?.username;
        this.profile$ = this.isPrincipal
          ? this.profileContextService.observeProfile()
          : this.profileService.getProfileByUsername(this.username);
      });

    this.route.queryParamMap
      .pipe(filter(paramMap => paramMap.has(ProfilePageComponent.TAB_KEY_QUERY_PARAM)), takeUntil(this.onDestroySubject))
      .subscribe(paramMap => this.activeTab = this.getActiveTab(paramMap.get(ProfilePageComponent.TAB_KEY_QUERY_PARAM) as string));
  }

  private getActiveTab(tabAsString: string): ProfileTabEnum {
    if (!this.isPrincipal) {
      return ProfileTabEnum.PROFILE;
    }

    switch (tabAsString) {
      case ProfileTabEnum.SETTINGS:
        return ProfileTabEnum.SETTINGS;
      case ProfileTabEnum.PASSWORD:
        return ProfileTabEnum.PASSWORD;
      case ProfileTabEnum.EDIT_PROFILE:
        return ProfileTabEnum.EDIT_PROFILE;
      default:
        return ProfileTabEnum.PROFILE;
    }
  }

  selectTab(tab: ProfileTabEnum): void {
    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: {tab},
        queryParamsHandling: 'merge',
      });
  }

  ngOnDestroy(): void {
    this.onDestroySubject.next();
    this.onDestroySubject.complete();
  }
}
