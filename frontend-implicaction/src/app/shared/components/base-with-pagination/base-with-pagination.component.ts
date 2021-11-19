import {Component} from '@angular/core';
import {Constants} from '../../../config/constants';
import {Pageable} from '../../models/pageable';

@Component({template: ''})
export class BaseWithPaginationComponent<T> {

  readonly DEFAULT_ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;
  isLoading = true;

  // Pagination
  pageable: Pageable<T> = Constants.PAGEABLE_DEFAULT;

  /**
   * Lance la pagination en invoquant la méthode {@link innerPaginate}
   * @param page numéro de la page
   * @param first indice du 1er élément à récupérer
   * @param rows nombre d'éléments par page
   */
  paginate({page, first, rows} = this.pageable): void {
    this.isLoading = true;
    if (page) {
      this.pageable.page = page;
    } else if (rows > 0) {
      this.pageable.page = first / rows;
    } else {
      this.pageable.page = 0;
    }
    this.pageable.first = first;
    this.pageable.rows = rows;

    this.innerPaginate();
  }

  /**
   * méthode à implémenter qui lance les appels au service voulu
   * @protected
   */
  protected innerPaginate(): void {
  }
}
