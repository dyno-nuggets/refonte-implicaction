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
    static readonly BY_ID = 'users';
  };

  static readonly RELATIONS = class {
    static readonly ALL_CONFIRMED_BY_USER_ID = 'relations';
  };
}
