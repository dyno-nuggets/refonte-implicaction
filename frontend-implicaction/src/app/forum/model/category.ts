export interface Category {
  id: number;
  title: string;
  description?: string;
  parentId?: number;
  children: Category[]
}
