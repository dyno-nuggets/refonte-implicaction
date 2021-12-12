import {Component, Input, OnInit} from '@angular/core';
import {Group} from '../../model/group';
import {GroupService} from '../../services/group.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {User} from '../../../shared/models/user';
import {AuthService} from '../../../shared/services/auth.service';
import {UserService} from '../../../user/services/user.service';
import {Univers} from '../../../shared/enums/univers';

@Component({
  selector: 'app-group-card',
  templateUrl: './group-card.component.html',
  styleUrls: ['./group-card.component.scss']
})
export class GroupCardComponent implements OnInit {

  @Input()
  group: Group;

  userGroupNames: string[] = [];
  currentUser: User;
  univers = Univers;

  constructor(
    private groupService: GroupService,
    private toasterService: ToasterService,
    private authService: AuthService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.userService
      .getUserGroups(this.currentUser.id)
      .subscribe(groups => this.userGroupNames = groups.map(group => group.name));
  }

  joinGroup(groupName: string): void {
    this.groupService.subscribeGroup(groupName)
      .subscribe(
        (groups) => {
          this.toasterService.success('Succès', `Vous avez adhéré au groupe !`);
          this.userGroupNames = groups.map(group => group.name);
        },
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la souscription au groupe')
      );
  }

}
