import {Component} from '@angular/core';
import {BaseWithPaginationAndFilterComponent} from '../../../shared/components/base-with-pagination-and-filter/base-with-pagination-and-filter.component';
import {Group} from '../../../discussion/model/group';
import {ActivatedRoute} from '@angular/router';
import {ToasterService} from '../../../core/services/toaster.service';
import {GroupService} from '../../../discussion/services/group.service';
import {finalize, take} from 'rxjs/operators';
import {Criteria} from '../../../shared/models/Criteria';

@Component({
  selector: 'app-pending-group-table',
  templateUrl: './pending-group-table.component.html',
  styleUrls: ['./pending-group-table.component.scss']
})
export class PendingGroupTableComponent extends BaseWithPaginationAndFilterComponent<Group, Criteria> {

  isLoading = true;
  selectedOrderCode: string;
  rowsPerPage = this.pageable.rowsPerPages[0];

  constructor(
    private toastService: ToasterService,
    private groupService: GroupService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  validateGroup(group: Group): void {
    this.groupService
      .validateGroup(group.name)
      .subscribe(
        () => this.paginate(this.pageable),
        () => this.toastService.error('Oops', `Une erreur est survenue lors de la validation du groupe.`),
        () => this.toastService.success('Succès', `Le groupe  ${group.name} est désormais validé.`),
      );
  }

  protected innerPaginate(): void {
    this.groupService
      .getAllPendingGroup(this.pageable)
      .pipe(
        take(1),
        finalize(() => this.isLoading = false)
      )
      .subscribe(
        data => {
          this.pageable.totalPages = data.totalPages;
          this.pageable.rows = data.size;
          this.pageable.totalElements = data.totalElements;
          this.pageable.content = data.content;
        },
        () => this.toastService.error('Oops', 'Une erreur est survenue lors de la récupération des données'),
      );
  }
}
