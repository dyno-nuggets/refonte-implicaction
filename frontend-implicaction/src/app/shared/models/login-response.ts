import {User} from './user';

export interface LoginResponse {
  authenticationToken: string;
  refreshToken: string;
  expiresAt: Date;
  currentUser: User;
}
