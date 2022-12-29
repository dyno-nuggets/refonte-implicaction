import {User} from "../../shared/models/user";
import {Topic} from "./topic"

export interface Response {
  id: number;
  message: string;
  createdAt: number;
  editedAt: number;
  user: User;
  topic: Topic;
}
