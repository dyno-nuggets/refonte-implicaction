import {Profile} from "../profile/profile";
import {RelationTypeCode} from "./relation-type.enum";

export interface Relation {
  id?: string;
  confirmedAt?: string;
  sentAt?: string;
  receiver?: Profile;
  sender?: Profile;
  relationWithCurrentUser?: RelationTypeCode;
}
