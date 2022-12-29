export interface Pageable<T> {
  rows: number;
  page?: number;
  sortBy?: string;
  sortOrder?: string;
  content?: T[];
  rowsPerPages?: number[];
  totalPages?: number;
  totalElements?: number;
  first?: number;
  last?: boolean;
  empty?: boolean;
}
