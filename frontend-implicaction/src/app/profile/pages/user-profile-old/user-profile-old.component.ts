import {Component, OnDestroy, OnInit} from '@angular/core';
import {ToasterService} from '../../../core/services/toaster.service';
import {ActivatedRoute} from '@angular/router';
import {UserContextService} from '../../../shared/services/user-context.service';
import {Subscription} from 'rxjs';
import {AuthService} from '../../../shared/services/auth.service';
import {finalize, map} from 'rxjs/operators';
import {Profile} from "../../models/profile";
import {ProfileService} from "../../services/profile.service";

@Component({
  selector: 'app-user-profile-old',
  templateUrl: './user-profile-old.component.html',
  styleUrls: ['./user-profile-old.component.scss']
})
export class UserProfileOldComponent implements OnInit, OnDestroy {

  profile: Profile;
  canEdit = false;
  isLoading = true;
  private subscription: Subscription;

  private sortByDateDesc = (a, b) => a.date ? new Date(b.date).getFullYear() - new Date(a.date).getFullYear() : -1;

  constructor(
    private authService: AuthService,
    private profileService: ProfileService,
    private toasterService: ToasterService,
    private route: ActivatedRoute,
    private userContexteService: UserContextService
  ) {
  }

  ngOnInit(): void {

    this.route.paramMap.subscribe(paramMap => {
      const userId = paramMap.get('userId');
      this.profileService
        .getProfileByUsername(userId)
        .pipe(finalize(() => this.isLoading = false),
          map(user => {
              // tri des dates par ordre décroissant
              user.trainings = [...user.trainings].sort(this.sortByDateDesc);
              user.experiences = [...user.experiences].sort(this.sortByDateDesc);
              return user;
            }
          )
        )
        .subscribe(
          profile => {
            this.profile = profile;
            this.canEdit = profile.username === this.authService.getCurrentUser()?.username;
            this.userContexteService.setUser(profile);
          },
          () => this.toasterService.error('Oops', 'Une erreur est survenue !')
        );
    });
    this.subscription = this.userContexteService
      .observeUser()
      // fixme: modifier ce cast
      .subscribe(user => this.profile = user as Profile);
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
