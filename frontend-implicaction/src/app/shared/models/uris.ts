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
    static readonly UPDATE_IMAGE = 'users/image';
    static readonly GET_FRIENDS = 'users';
    static readonly GET_FRIEND_REQUEST_RECEIVED = 'users/friends/received';
    static readonly GET_FRIEND_REQUEST_SENT = 'users/friends/sent';
    static readonly GET_ALL_PENDING_USERS = 'users/pending';
    static readonly SUBSCRIBE_GROUP = 'subscribe';
    static readonly GROUP_LIST = 'users';
    static readonly GROUP_SUBSCRIBE = 'users/group-subscribe';
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
    static readonly TOGGLE_ARCHIVE = 'job-postings/archive';
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

  /**
   * GROUPS
   */
  static GROUP = class {
    static readonly BASE_URI = 'groups';
    static readonly TOP_POSTING = 'groups/top-posting';
    static readonly CREATE_NO_IMAGE = 'groups/no-image';
  };

  /**
   * COMMENTS
   */
  static COMMENTS = class {
    static readonly BASE_URI = 'comments';
  };

  /**
   * JOB-APPLICATIONS
   */
  static JOB_APPLICATION = class {
    static readonly BASE_URI = 'applies';
  };
}
