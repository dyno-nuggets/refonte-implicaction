export class Uris {
  /**
   * Auth
   */
  static readonly AUTH = class {
    static readonly LOGIN = 'auth/login';
    static readonly SIGNUP = 'auth/signup';
    static readonly REFRESH_TOKEN = 'auth/refresh/token';
    static readonly LOGOUT = 'auth/logout';
  };

  /**
   * USERS
   */
  static readonly USERS = class {
    static readonly ALL = 'users';
    static readonly USER_BY_ID = 'users';
  };
}
