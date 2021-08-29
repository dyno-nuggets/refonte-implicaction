import {Pageable} from '../shared/models/pageable';

export class Constants {
  public static readonly API_ENDPOINT: string = '/api';
  public static readonly API_MOCK_ENDPOINT: string = 'mock-domain/api';
  public static readonly PAGEABLE_DEFAULT: Pageable = {
    page: 0,
    size: 10,
    totalElements: 0,
    first: true,
    content: [],
    totalPages: 0,
    empty: true,
    last: true
  };
}
