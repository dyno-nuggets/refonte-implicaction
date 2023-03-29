import {RoleEnumCode} from '../enums/role.enum';

export interface User {
  id?: string;
  username?: string;
  firstname?: string;
  lastname?: string;
  birthday?: string;
  email?: string;
  registeredAt?: string;
  activationKey?: string;
  enabled?: boolean;
  emailVerified?: boolean;
  roles?: RoleEnumCode[];
}
