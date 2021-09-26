import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../../../shared/models/user';
import {ToasterService} from '../../../core/services/toaster.service';
import {ActivatedRoute} from '@angular/router';
import {ExperiencesContexteService} from '../../../shared/services/experiences-contexte.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user: User = {};

  constructor(
    private userService: UserService,
    private toasterService: ToasterService,
    private route: ActivatedRoute,
    private ecs: ExperiencesContexteService
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      const userId = paramMap.get('userId');
      this.userService
        .getUserById(userId)
        .subscribe(
          user => {
            this.user = user;
            this.ecs.setExperiences(user.experiences);
          },
          () => this.toasterService.error('oops', 'Une erreur est survenue !')
        );
    });
  }

}
