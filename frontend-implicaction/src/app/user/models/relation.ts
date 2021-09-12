import {User} from '../../shared/models/user';

export interface Relation {
  id?: string;
  confirmedAt?: string;
  sentAt?: string;
  receiver?: User;
  sender?: User;
}
