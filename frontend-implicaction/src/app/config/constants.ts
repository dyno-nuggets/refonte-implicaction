import {Pageable} from '../shared/models/pageable';

export class Constants {
  public static readonly API_ENDPOINT: string = '/api';
  public static readonly API_MOCK_ENDPOINT: string = 'mock-domain/api';
  public static readonly PAGEABLE_DEFAULT: Pageable = {
    page: 0,
    rows: 10,
    totalElements: 0,
    first: 0,
    content: [],
    rowsPerPages: [10, 25, 50],
    totalPages: 0,
    empty: true,
    last: true
  };
  public static readonly DATE_REGEX = /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/;
}
