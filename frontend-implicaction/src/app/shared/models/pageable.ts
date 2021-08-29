export interface Pageable<T = any> {
  size: number;
  page?: number;
  content?: T[];
  totalPages?: number;
  totalElements?: number;
  first?: boolean;
  last?: boolean;
  empty?: boolean;
}
