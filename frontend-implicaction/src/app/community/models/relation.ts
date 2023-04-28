import {Profile} from "./profile";

export interface Relation {
  id?: string;
  confirmedAt?: string;
  sentAt?: string;
  receiver?: Profile;
  sender?: Profile;
}
