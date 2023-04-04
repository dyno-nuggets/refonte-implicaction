import {Profile} from "../../profile/models/profile";
import {RelationType} from "./relation-type.enum";

export interface Relation {
  id?: string;
  confirmedAt?: string;
  sentAt?: string;
  receiver?: Profile;
  sender?: Profile;
  relationType?: RelationType;
}
