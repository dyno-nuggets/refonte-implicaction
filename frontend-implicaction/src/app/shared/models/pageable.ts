export interface Pageable<T = any> {
  size: number;
  page?: number;
  content?: T[];
  rowsPerPages?: number[];
  totalPages?: number;
  totalElements?: number;
  first?: number;
  last?: boolean;
  empty?: boolean;
}
