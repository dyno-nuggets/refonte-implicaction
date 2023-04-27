import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Pageable} from "../../models/pageable";
import {Constants} from "../../../config/constants";

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
})
export class PaginatorComponent {

  readonly DEFAULT_ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;

  @Input() pageable: Pageable<any>;

  @Output() pageChange = new EventEmitter<number>();

  changePage(page: number): void {
    this.pageChange.emit(page);
  }
}
