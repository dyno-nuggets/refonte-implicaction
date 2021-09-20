import {RoleEnum} from '../enums/role-enum.enum';

export interface SignupRequestPayload {
  username: string;
  email: string;
  password: string;
  nicename: string;
  roles: RoleEnum[];
}
