import {Component, OnInit} from '@angular/core';
import {UserService} from '../shared/services/user.service';
import {User} from '../shared/models/user';
import {Constants} from '../config/constants';
import {ToasterService} from '../core/services/toaster.service';

@Component({
  selector: 'app-community',
  templateUrl: './community.component.html',
  styleUrls: ['./community.component.scss']
})
export class CommunityComponent implements OnInit {

  users: User[] = [];
  pageable = Constants.PAGEABLE_DEFAULT;

  constructor(
    private userService: UserService,
    private toastService: ToasterService
  ) {
  }

  ngOnInit(): void {
    this.userService
      .getAll(this.pageable)
      .subscribe(
        userPageable => {
          this.pageable = userPageable;
          this.users = userPageable.content;
          console.log(this.users);
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des utilisateurs')
      );
  }

}
