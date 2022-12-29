import {Component} from '@angular/core';
import {Constants} from '../../../config/constants';
import {Pageable} from '../../models/pageable';
import {ActivatedRoute} from '@angular/router';
import {Criteria} from '../../models/Criteria';

@Component({template: ''})
export class BaseWithPaginationAndFilterComponent<T, C extends Criteria> {

  readonly DEFAULT_ROWS_PER_PAGE_OPTIONS = Constants.ROWS_PER_PAGE_OPTIONS;

  isLoading = true;
  criteria: C;

  // Pagination
  pageable: Pageable<T> = {...Constants.PAGEABLE_DEFAULT};

  constructor(protected route: ActivatedRoute) {
  }

  /**
   * Lance la pagination en invoquant la méthode {@link innerPaginate}
   * @param page numéro de la page
   * @param first indice du 1er élément à récupérer
   * @param rows nombre d'éléments par page
   */
  paginate({page, first, rows} = this.pageable as Pageable<T>): void {
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
   * default trackBy function
   */
  trackByItemId = (index: number, item: T) => (item as any).id;

  protected innerPaginate(): void {
    // Méthode à implémenter qui s'occupe de charger les données. Elle est appelée par la méthode paginate
  }

  /**
   * Méthode qui ajoute les paramètres de l'url dans la variable correspondante
   * la variable correspond à un critère de recherche ou à la pagination
   * @param filterKeys ensemble des clés à récupérer depuis les query params de l'url
   */
  protected async getFilterFromQueryParams(filterKeys: string[]): Promise<void> {
    const pageableKeys = ['rows', 'page', 'sortOrder', 'sortBy'];
    return new Promise(resolve => {
      this.route
        .queryParams
        .subscribe(params => {
          Object.entries(params)
            .forEach(([key, value]) => {
              if (filterKeys.includes(key)) {
                this.criteria[key] = value;
              } else if (pageableKeys.includes(key)) {
                this.pageable[key] = value;
              }
            });
          return resolve();
        });
    });
  }

  /**
   * @return any les filtres de recherche auxquels sont ajoutés les filtres de pagination
   */
  protected buildQueryParams(): any {
    return {
      ...this.criteria,
      size: this.pageable.rows,
      page: this.pageable.page,
      sortBy: this.pageable.sortBy,
      sortOrder: this.pageable.sortOrder
    };
  }
}
