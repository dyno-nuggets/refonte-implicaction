export interface CategoryWithParent {
  id: number;
  title: string;
  description?: string;
  parent?: CategoryWithParent;
}
