import {RoleEnum} from '../enums/role-enum.enum';

export interface LoginResponse {
  authenticationToken: string;
  refreshToken: string;
  expiresAt: Date;
  username: string;
  userId: string;
  roles: RoleEnum[];
}
