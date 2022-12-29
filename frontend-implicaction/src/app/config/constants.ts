import {Pageable} from '../shared/models/pageable';

export class Constants {
  public static readonly API_ENDPOINT: string = '/api';
  public static readonly API_MOCK_ENDPOINT: string = 'mock-domain/api';
  public static readonly PAGEABLE_DEFAULT: Pageable<any> = {
    page: 0,
    rows: 10,
    totalElements: 0,
    first: 0,
    content: [],
    sortOrder: 'ASC',
    sortBy: 'id',
    rowsPerPages: [10, 25, 50],
    totalPages: 0,
    empty: true,
    last: true
  };
  /**
   * Cette constante est utilisée pour outrepasser la pagination d'une réponse : la page contiendra tous les résultats entre 0 et la
   * valeur maximale d'un entier en java
   */
  public static readonly ALL_VALUE_PAGEABLE: Pageable<any> = {
    page: 0,
    rows: 2147483647, // correspond à Integer.MAX_VALUE
  };

  public static readonly DATE_REGEX = /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/;

  public static readonly ROWS_PER_PAGE_OPTIONS = [10, 25, 50];

  public static readonly USER_IMAGE_DEFAULT_URI = 'assets/img/avatar-ia-user.png';

  public static readonly GROUP_IMAGE_DEFAULT_URI = 'assets/img/avatar-ia-group.png';

  public static readonly COMPANY_IMAGE_DEFAULT_URI = 'assets/img/avatar-ia-group.png';

  public static readonly LATEST_JOBS_COUNT = 4;

  public static readonly LATEST_POSTS_COUNT = 3;

  public static readonly DEFAULT_YEAR_RANGE = `1900:${new Date().getFullYear() + 1}`;
}
