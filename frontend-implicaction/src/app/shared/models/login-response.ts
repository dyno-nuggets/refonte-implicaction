import {RoleEnumCode} from '../enums/role.enum';

export interface LoginResponse {
  authenticationToken: string;
  refreshToken: string;
  expiresAt: Date;
  username: string;
  userId: string;
  roles: RoleEnumCode[];
}
