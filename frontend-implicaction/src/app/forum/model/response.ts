import {Topic} from "./topic"
import {Profile} from "../../profile/models/profile";

export interface Response {
  id: number;
  message: string;
  createdAt: number;
  editedAt: number;
  author: Profile;
  topic: Topic;
}
