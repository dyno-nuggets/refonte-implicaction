import {LastCategoryUpdate} from './lastCategoryUpdate';

export interface Category {
  id: number;
  title: string;
  description?: string;
  parentId?: number;
  children: number[];
  lastUpdate?: LastCategoryUpdate;
}
