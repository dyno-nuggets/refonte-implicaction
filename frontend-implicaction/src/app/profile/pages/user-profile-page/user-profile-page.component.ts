import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProfileService} from "../../services/profile.service";
import {Profile} from "../../models/profile";
import {Univers} from "../../../shared/enums/univers";
import {Observable, Subject} from "rxjs";
import {ProfileTabEnum} from "../../models/enums/profile-tab-enum";
import {filter, takeUntil} from "rxjs/operators";
import {AuthService} from "../../../shared/services/auth.service";

@Component({
  selector: 'app-user-profile-page',
  templateUrl: './user-profile-page.component.html',
  styleUrls: ['./user-profile-page.component.scss']
})
export class UserProfilePageComponent implements OnInit, OnDestroy {
  profile$: Observable<Profile>;
  univers = Univers;
  activeTab: ProfileTabEnum = ProfileTabEnum.PROFILE;
  tabEnum = ProfileTabEnum;
  username: string;

  private static readonly TAB_KEY_QUERY_PARAM = 'tab';
  private static readonly USER_PARAM_NAME = 'username'

  private notifier = new Subject();

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private profileService: ProfileService,
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap
      .pipe(filter(paramMap => paramMap.has(UserProfilePageComponent.USER_PARAM_NAME)), takeUntil(this.notifier))
      .subscribe(paramMap => {
        this.username = paramMap.get(UserProfilePageComponent.USER_PARAM_NAME);
        this.profile$ = this.profileService.getProfileByUsername(this.username);
      });

    this.route.queryParamMap
      .pipe(filter(paramMap => paramMap.has(UserProfilePageComponent.TAB_KEY_QUERY_PARAM)), takeUntil(this.notifier))
      .subscribe(
        paramMap => this.activeTab = this.getActiveTab(paramMap.get(UserProfilePageComponent.TAB_KEY_QUERY_PARAM) as string)
      );
  }

  private getActiveTab(tabAsString: string): ProfileTabEnum {
    if (this.username !== this.authService.getCurrentUser()?.username) {
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
    this.notifier.next();
    this.notifier.complete();
  }
}
