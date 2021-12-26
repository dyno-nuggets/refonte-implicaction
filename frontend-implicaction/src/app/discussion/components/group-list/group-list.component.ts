import {Component, OnInit} from '@angular/core';
import {BaseWithPaginationAndFilterComponent} from '../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {Group} from '../../model/group';
import {Criteria} from '../../../shared/models/Criteria';
import {ActivatedRoute} from '@angular/router';
import {GroupService} from '../../services/group.service';
import {Constants} from '../../../config/constants';
import {finalize} from 'rxjs/operators';
import {ToasterService} from '../../../core/services/toaster.service';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.scss']
})
export class GroupListComponent extends BaseWithPaginationAndFilterComponent<Group, Criteria> implements OnInit {

  readonly ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;
  isLoading = true;

  constructor(
    private toastService: ToasterService,
    private groupService: GroupService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  ngOnInit(): void {
    this.paginate();
  }

  protected innerPaginate(): void {
    this.groupService
      .getAllGroups(this.pageable)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération de la liste des groupes')
      );
  }

}
