import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {PaginationUpdate} from '../../models/pagination-update';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PaginatorComponent implements OnChanges {

  @Input() page: number;
  @Input() totalPages: number;
  @Input() alwaysShow: boolean;

  @Output() pageChange = new EventEmitter<PaginationUpdate>();

  first: boolean;
  last: boolean;
  visible: boolean;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.hasOwnProperty('page') || changes.hasOwnProperty('totalPages')) {
      this.first = !this.page;
      this.last = this.page >= (this.totalPages - 1);
      this.visible = this.alwaysShow || this.totalPages > 1;
    }
  }

  changePage(page: number): void {
    this.pageChange.emit({page});
  }
}
