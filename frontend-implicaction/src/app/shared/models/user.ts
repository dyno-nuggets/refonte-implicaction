import {WorkExperience} from './work-experience';
import {Training} from './training';
import {RelationType} from '../../user/models/relation-type.enum';
import {RoleEnumCode} from '../enums/role.enum';

export interface User {
  id?: string;
  username?: string;
  email?: string;
  url?: string;
  registeredAt?: string;
  activatedAt?: string;
  active?: boolean;
  activationKey?: string;
  status?: string;
  firstname?: string;
  lastname?: string;
  birthday?: string;
  phoneNumber?: string;
  hobbies?: string;
  presentation?: string;
  purpose?: string;
  expectation?: string;
  contribution?: string;
  armyCorps?: string;
  rank?: string;
  experiences?: WorkExperience[];
  trainings?: Training[];
  relationTypeOfCurrentUser?: RelationType;
  roles?: RoleEnumCode[];
  imageUrl?: string;
}
