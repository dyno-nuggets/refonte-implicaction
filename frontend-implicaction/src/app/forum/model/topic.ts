import {Category} from "./category";
import {User} from "../../shared/models/user";
import {Response} from "./response";

export interface Topic {
  id: number;
  title: string;
  message: string;
  createdAt: number;
  editedAt: number;
  isLocked: boolean;
  isPinned: boolean;
  author: User;
  response: Response[];
  category: Category;
}
