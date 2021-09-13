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
    static readonly GET_FRIENDS = 'users';
    static readonly GET_FRIEND_REQUEST_RECEIVED = 'users/friends/received';
    static readonly GET_FRIEND_REQUEST_SENT = 'users/friends/sent';
  };

  /**
   * RELATIONS
   */
  static readonly RELATIONS = class {
    static readonly BASE_URI = 'relations';
    static readonly ALL_BY_USER_ID = 'relations/list';
    static readonly REQUEST = 'relations/request';
  };
}
