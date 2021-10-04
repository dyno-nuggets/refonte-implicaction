import {RoleEnumCode} from '../enums/role.enum';

export interface SignupRequestPayload {
  username: string;
  email: string;
  password: string;
  firstname: string;
  lastname: string;
  roles: RoleEnumCode[];
}
