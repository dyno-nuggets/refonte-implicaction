export class Uris {

  /**
   * App
   */
  static readonly APP = class {
    static readonly BASE_URI = 'public/app';
  };

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
    static readonly BASE_URI = 'users';
    static readonly COMMUNITY_LIST = 'users/community';
    static readonly BY_ID = 'users';
    static readonly GROUP_LIST = 'users';
    static readonly ACTIVATE_USER = 'users/{username}/enable';
    static readonly UPDATE_USER_ROLES = 'users/{username}/roles';
    static readonly ENABLED_USERS_COUNT = 'users/total';
  };

  static readonly PROFILES = class {
    static readonly BASE_URI = 'profiles';
    static readonly BY_USERNAME = 'profiles';
    static readonly POST_AVATAR = 'profiles/{username}/avatar';
    static readonly REQUEST_AS_FRIEND_COUNT = 'profiles/friend-requests/count';
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
    static readonly ALL_BY_USERNAME = 'relations';
    static readonly GET_ALL_COMMUNITY = 'relations/community';
    static readonly GET_FRIEND_REQUEST_RECEIVED = 'relations';
    static readonly GET_FRIEND_REQUEST_SENT = 'relations';
    static readonly REQUEST = 'relations';
  };

  /**
   * JOBS
   */
  static readonly JOBS = class {
    static readonly BASE_URI = 'job-postings';
    static readonly LATEST_JOBS = 'job-postings/latest';
    static readonly GET_ALL_PENDING_JOBS = 'job-postings/pending';
    static readonly VALIDATE_JOB = 'job-postings/validate';
    static readonly GET_VALIDATED_JOBS = 'job-postings/validated';
    static readonly TOGGLE_ARCHIVE = 'job-postings/archive';
    static readonly COUNT = 'job-postings/count';
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
    static readonly LATEST_POSTS = 'posts/latest';
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
    static readonly GET_ALL_PENDING_GROUPS = 'groups/pending';
    static readonly VALIDATED_GROUPS = 'groups/enabled';
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

  /**
   * FORUM
   */
  static FORUM = class {
    static readonly BASE_URI = 'forums';
    static readonly CATEGORIES = 'forums/categories';
    static readonly TOPICS = 'forums/topics';
    static readonly LATEST_TOPICS = 'forums/topics/latest';

    static readonly CATEGORIES_TOPICS = (id: number) => `${Uris.FORUM.CATEGORIES}/${id}/topics`;
    static readonly TOPICS_RESPONSES = (id: number) => `${Uris.FORUM.TOPICS}/${id}/responses`;
  };
}
