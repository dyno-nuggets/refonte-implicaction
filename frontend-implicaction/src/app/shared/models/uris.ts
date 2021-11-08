export class Uris {
  /**
   * Auth
   */
  static readonly AUTH = class {
    static readonly LOGIN = 'auth/login';
    static readonly SIGNUP = 'auth/signup';
    static readonly REFRESH_TOKEN = 'auth/refresh/token';
    static readonly LOGOUT = 'auth/logout';
    static readonly ACTIVATE_USER = 'auth/accountVerification';
  };

  /**
   * USERS
   */
  static readonly USERS = class {
    static readonly BASE_URI = 'users';
    static readonly COMMUNITY_LIST = 'users/community';
    static readonly BY_ID = 'users';
    static readonly GET_FRIENDS = 'users';
    static readonly GET_FRIEND_REQUEST_RECEIVED = 'users/friends/received';
    static readonly GET_FRIEND_REQUEST_SENT = 'users/friends/sent';
    static readonly GET_ALL_PENDING_USERS = 'users/pending';
  };

  /**
   * EXPERIENCES
   */
  static readonly EXPERIENCE = class {
    static readonly BASE_URI = 'experiences';
  };

  /**
   * TRAININGS
   */
  static readonly TRAINING = class {
    static readonly BASE_URI = 'trainings';
  };

  /**
   * RELATIONS
   */
  static readonly RELATIONS = class {
    static readonly BASE_URI = 'relations';
    static readonly ALL_BY_USER_ID = 'relations/list';
    static readonly REQUEST = 'relations/request';
  };

  /**
   * JOBS
   */
  static readonly JOBS = class {
    static readonly BASE_URI = 'job-postings';
  };

  /**
   * COMPANIES
   */
  static readonly COMPANIES = class {
    static readonly BASE_URI = 'companies';
  };

  /**
   * CONTRACTS
   */
  static readonly CONTRACTS = class {
    static readonly BASE_URI = 'contracts';
  };

  /**
   * POSTS
   */
  static readonly POSTS = class {
    static readonly BASE_URI = 'posts';
  };

  /**
   * VOTES
   */
  static readonly VOTES = class {
    static readonly BASE_URI = 'votes';
  };
}
