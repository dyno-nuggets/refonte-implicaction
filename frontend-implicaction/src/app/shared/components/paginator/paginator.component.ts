import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from '@angular/core';
import {Pageable} from "../../models/pageable";

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PaginatorComponent {

  @Input() pageable: Pageable<any>;

  @Output() pageChange = new EventEmitter<number>();

  changePage(page: number): void {
    this.pageChange.emit(page);
  }
}
