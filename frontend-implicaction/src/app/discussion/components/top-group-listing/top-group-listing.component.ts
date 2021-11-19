import {Component, Input, OnInit} from '@angular/core';
import {Group} from '../../model/group';
import {GroupService} from '../../services/group.service';
import {ToasterService} from '../../../core/services/toaster.service';
import {SidebarService} from '../../../shared/services/sidebar.service';
import {CreateGroupFormComponent} from '../create-group-form/create-group-form.component';
import {finalize} from 'rxjs/operators';
import {Constants} from '../../../config/constants';

@Component({
  selector: 'app-top-group-listing',
  templateUrl: './top-group-listing.component.html',
  styleUrls: ['./top-group-listing.component.scss']
})
export class TopGroupListingComponent implements OnInit {

  readonly GROUP_IMG_DEFAULT_URI = Constants.GROUP_IMAGE_DEFAULT_URI;

  @Input()
  limit = 5;

  groups: Group[] = [];
  isLoading = true;

  constructor(
    private groupService: GroupService,
    private toasterService: ToasterService,
    private sidebarService: SidebarService
  ) {
  }

  ngOnInit(): void {
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
}
