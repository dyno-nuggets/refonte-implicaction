import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../../shared/models/user';
import {ToasterService} from '../../../core/services/toaster.service';
import {ActivatedRoute} from '@angular/router';
import {UserContextService} from '../../../shared/services/user-context.service';
import {Subscription} from 'rxjs';
import {AuthService} from '../../../shared/services/auth.service';
import {finalize, map} from 'rxjs/operators';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit, OnDestroy {

  user: User = {};
  canEdit = false;
  isLoading = true;
  private subscription: Subscription;

  private sortByDateDesc = (a, b) => a.date ? new Date(b.date).getFullYear() - new Date(a.date).getFullYear() : -1;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private toasterService: ToasterService,
    private route: ActivatedRoute,
    private userContexteService: UserContextService
  ) {
  }

  ngOnInit(): void {

    this.route.paramMap.subscribe(paramMap => {
      const userId = paramMap.get('userId');
      this.userService
        .getUserById(userId)
        .pipe(
          finalize(() => this.isLoading = false),
          map(user => {
              // tri des dates par ordre décroissant
              user.trainings = [...user.trainings].sort(this.sortByDateDesc);
              user.experiences = [...user.experiences].sort(this.sortByDateDesc);
              return user;
            }
          )
        )
        .subscribe(
          user => {
            this.user = user;
            this.canEdit = user.id === this.authService.getCurrentUser()?.id;
            this.userContexteService.setUser(user);
          },
          () => this.toasterService.error('Oops', 'Une erreur est survenue !')
        );
    });
    this.subscription = this.userContexteService
      .observeUser()
      .subscribe(user => this.user = user);
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

}
