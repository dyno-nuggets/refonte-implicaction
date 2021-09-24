import {WorkExperience} from './work-experience';
import {Training} from './training';
import {RelationType} from '../../user/models/relation-type.enum';
import {RoleEnum} from '../enums/role-enum.enum';

export interface User {
  id?: string;
  username?: string;
  email?: string;
  url?: string;
  registered?: string;
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
  roles?: RoleEnum[];
}
