import {Component, Input, OnInit} from '@angular/core';
import {Group} from '../../model/group';
import {GroupService} from '../../services/group.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {CreateGroupFormComponent} from '../create-group-form/create-group-form.component';
import {finalize} from 'rxjs/operators';
import {Constants} from '../../../config/constants';
import {AuthService} from '../../../shared/services/auth.service';
import {UserService} from '../../../user/services/user.service';
import {User} from '../../../shared/models/user';
import {Univers} from '../../../shared/enums/univers';

@Component({
  selector: 'app-top-group-listing',
  templateUrl: './top-group-listing.component.html',
  styleUrls: ['./top-group-listing.component.scss']
})
export class TopGroupListingComponent implements OnInit {

  readonly GROUP_IMG_DEFAULT_URI = Constants.GROUP_IMAGE_DEFAULT_URI;

  @Input()
  limit = 5;

  univers = Univers;
  groups: Group[] = [];
  isLoading = true;
  userGroupNames: string[] = [];
  currentUser: User;
  canSubscribe: boolean;

  constructor(
    private groupService: GroupService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService,
    private authService: AuthService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.userService
      .getUserGroups(this.currentUser.id)
      .subscribe(groups => this.userGroupNames = groups.map(group => group.name));

    this.groupService
      .findByTopPosting(this.limit)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        groups => this.groups = groups,
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors du chargement de la liste de groupes')
      )
    ;
  }

  openSidebarCreationGroup(): void {
    this.sidebarService
      .open({
        component: CreateGroupFormComponent,
        title: 'Créer un groupe',
        width: 650
      });
  }

  joinGroup(groupName: string): void {
    this.groupService.subscribeGroup(groupName)
      .subscribe(
        (groups) => {
          this.toasterService.success('Félicitation', `Vous avez rejoint le groupe ${groupName} !`);
          this.userGroupNames = groups.map(group => group.name);
        },
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la souscription au groupe')
      );
  }

}
