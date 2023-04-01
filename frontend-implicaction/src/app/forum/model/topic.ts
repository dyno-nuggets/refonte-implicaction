import {Category} from "./category";
import {Response} from "./response";
import {Profile} from "../../profile/models/profile";

export interface Topic {
  id: number;
  title: string;
  message: string;
  createdAt: number;
  editedAt: number;
  locked: boolean;
  pinned: boolean;
  author: Profile;
  response: Response[];
  category: Category;
  responsesCount: number;
  lastResponse?: Response;
  durationAsString?: string;
}
