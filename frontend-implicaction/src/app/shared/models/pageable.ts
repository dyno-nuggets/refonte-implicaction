export interface Pageable<T = any> {
  size: number;
  page?: number;
  content?: T[];
  rowsPerPages?: number[];
  totalPages?: number;
  totalElements?: number;
  first?: boolean;
  last?: boolean;
  empty?: boolean;
}
