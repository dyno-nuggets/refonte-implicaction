import {Component} from '@angular/core';
import {Constants} from '../../../config/constants';
import {Pageable} from '../../models/pageable';

@Component({template: ''})
export class BaseWithPaginationComponent<T> {

  readonly DEFAULT_ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;
  isLoading = true;

  // Pagination
  pageable: Pageable<T> = Constants.PAGEABLE_DEFAULT;

  paginate({page, first, rows} = this.pageable): void {
    this.isLoading = true;
    this.pageable.page = page;
    this.pageable.first = first;
    this.pageable.rows = rows;

    this.innerPaginate();
  }

  protected innerPaginate(): void {
  }
}
