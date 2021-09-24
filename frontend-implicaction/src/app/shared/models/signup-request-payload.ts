import {RoleEnum} from '../enums/role-enum.enum';

export interface SignupRequestPayload {
  username: string;
  email: string;
  password: string;
  firstname: string;
  lastname: string;
  roles: RoleEnum[];
}
