import {Component} from '@angular/core';
import {BaseWithPaginationComponent} from '../../../shared/components/base-with-pagination/base-with-pagination.component';
import {Group} from '../../../discussion/model/group';
import {SortDirectionEnum} from '../../../shared/enums/sort-direction.enum';
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
export class PendingGroupTableComponent extends BaseWithPaginationComponent<Group, Criteria> {

  isLoading = true;
  selectedOrderCode: string;
  sortDirection = SortDirectionEnum;
  rowsPerPage = this.pageable.rowsPerPages[0];

  constructor(
    private toastService: ToasterService,
    private groupService: GroupService,
    protected route: ActivatedRoute
  ) {
    super(route);
  }

  activateGroup(group: Group): void {
    this.groupService
      .activateGroup(group)
      .subscribe(
        (data) => {
          this.paginate({first: this.pageable.first, rows: this.pageable.rows});
        },
        () => this.toastService.error('Oops', `Une erreur est survenue lors de la validation du groupe.`),
        () => this.toastService.success('Succès', `Le groupe  ${group.name} est désormais actif.`),
      );
  }

  protected innerPaginate(): void {
    this.groupService
      .getAllPendingActivationGroup(this.pageable)
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
