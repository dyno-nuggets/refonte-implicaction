export interface EditCategoryPayload {

  id: number;
  title: string;
  description: string;
  parentId: number | null;
}
