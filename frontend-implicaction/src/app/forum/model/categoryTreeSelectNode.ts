export interface CategoryTreeSelectNode {
  id: number;
  label: string;
  data: string;
  selectable: boolean;
  children?: CategoryTreeSelectNode[];
}
