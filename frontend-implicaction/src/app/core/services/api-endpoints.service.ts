import {Injectable} from '@angular/core';
import {UrlBuilder} from '../../shared/classes/url-builder';
import {QueryStringParameters} from '../../shared/classes/query-string-parameters';
import {Constants} from '../../config/constants';
import {Uris} from '../../shared/models/uris';
import {Pageable} from '../../shared/models/pageable';
import {JobCriteriaFilter} from '../../job/models/job-criteria-filter';
import {Criteria} from '../../shared/models/Criteria';

@Injectable({
  providedIn: 'root'
})
@Injectable()
export class ApiEndpointsService {

  // URL
  private static createUrl(action: string, isMockAPI = false): string {
    const urlBuilder: UrlBuilder = new UrlBuilder(
      isMockAPI ? Constants.API_MOCK_ENDPOINT : Constants.API_ENDPOINT,
      action
    );
    return urlBuilder.toString();
  }

  // URL WITH QUERY PARAMETERS
  private static createUrlWithQueryParameters(
    action: string,
    queryStringHandler?:
      (queryStringParameters: QueryStringParameters) => void
  ): string {
    const urlBuilder: UrlBuilder = new UrlBuilder(
      Constants.API_ENDPOINT,
      action
    );
    // Push extra query string params
    if (queryStringHandler) {
      queryStringHandler(urlBuilder.queryString);
    }
    return urlBuilder.toString();
  }

  // URL WITH PATH VARIABLES
  private static createUrlWithPathVariables(action: string, pathVariables: any[] = []): string {
    let encodedPathVariablesUrl = '';
    // Push extra path variables
    for (const pathVariable of pathVariables) {
      if (pathVariable !== null) {
        encodedPathVariablesUrl += `/${encodeURIComponent(pathVariable.toString())}`;
      }
    }
    const urlBuilder: UrlBuilder = new UrlBuilder(Constants.API_ENDPOINT, `${action}${encodedPathVariablesUrl}`);
    return urlBuilder.toString();
  }

  private static createUrlWithPageable(uri: string, pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithQueryParameters(
      uri,
      (qs: QueryStringParameters) => {
        if (pageable.page) {
          qs.push('page', pageable.page);
        }
        if (pageable.rows) {
          qs.push('rows', pageable.rows);
        }
        if (pageable.sortBy) {
          qs.push('sortBy', pageable.sortBy);
        }
        if (pageable.sortOrder) {
          qs.push('sortOrder', pageable.sortOrder);
        }
      });
  }

  private static concatCriterias(criteria: Criteria, pageable: Pageable<any>): any {
    return {
      ...criteria,
      rows: pageable.rows,
      page: pageable.page,
      sortBy: pageable.sortBy,
      sortOrder: pageable.sortOrder
    };
  }

  /**
   * Auth
   */

  getLoginEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.AUTH.LOGIN);
  }

  getSignUpEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.AUTH.SIGNUP);
  }

  getJwtRefreshTokenEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.AUTH.REFRESH_TOKEN);
  }

  getLogoutEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.AUTH.LOGOUT);
  }

  getUserByIdEndpoint(userId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.USERS.BY_ID, [userId]);
  }

  getActivateUserEndpoint(activationKey: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.AUTH.ACTIVATE_USER, [activationKey]);
  }

  /**
   * Users
   */

  getAllUserEndpoint(pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.USERS.BASE_URI, pageable);
  }

  updateImageProfileEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.USERS.UPDATE_IMAGE);
  }

  getAllUserCommunityEndpoint(pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.USERS.COMMUNITY_LIST, pageable);
  }

  getAllPendingActivationUsersEndpoint(pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.USERS.GET_ALL_PENDING_USERS, pageable);
  }

  getAllFriendsByUserIdEndPoint(userId: string, pageable: Pageable): string {
    const uri = ApiEndpointsService.createUrlWithPathVariables(Uris.USERS.GET_FRIENDS, [userId, 'friends'])
      // createUrlWithPathVariables et createUrlWithPageable ajoutent le endpoint ('/api/') de l'api en début de l'adresse générée
      // on se retrouve donc avec une répétition en les chaînant. Il faut donc en supprimer un
      .replace(`${Constants.API_ENDPOINT}/`, '');

    return ApiEndpointsService.createUrlWithPageable(uri, pageable);
  }

  getFriendRequestReceivedEndpoint(pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.USERS.GET_FRIEND_REQUEST_RECEIVED, pageable);
  }

  getFriendRequestSentEndPoint(pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.USERS.GET_FRIEND_REQUEST_SENT, pageable);
  }

  createRelationEndpoint(receiverId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.RELATIONS.REQUEST, [receiverId]);
  }

  getAllGroups(userId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.USERS.GROUP_LIST, [userId, 'groups']);
  }

  /**
   * Experiences
   */

  deleteExperienceEndpoint(experienceId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.EXPERIENCE.BASE_URI, [experienceId]);
  }

  /**
   * Trainings
   */

  updateExperienceEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.EXPERIENCE.BASE_URI);
  }

  createExperienceEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.EXPERIENCE.BASE_URI);
  }

  createTrainingEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.TRAINING.BASE_URI);
  }

  updateTrainingEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.TRAINING.BASE_URI);
  }

  deleteTrainingEndpoint(trainingId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.TRAINING.BASE_URI, [trainingId]);
  }

  updateUserEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.USERS.BASE_URI);
  }

  /**
   * Relations
   */

  getAllRelationsByUserIdEndpoint(userId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.RELATIONS.ALL_BY_USER_ID, [userId]);
  }

  confirmUserAsFriendEndpoint(senderId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.RELATIONS.BASE_URI, [senderId, 'confirm']);
  }

  cancelRelationByUserEndpoint(userId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.RELATIONS.BASE_URI, [userId, 'cancel']);
  }

  /**
   * Jobs
   */

  getAllJobEndpoint(pageable: Pageable, criteria: JobCriteriaFilter, archive: boolean, checkApply: boolean): string {
    // on merge les filtres et les attributs de pagination
    const objectParam = {
      ...criteria,
      rows: pageable.rows,
      page: pageable.page,
      sortBy: pageable.sortBy,
      sortOrder: pageable.sortOrder,
      checkApply,
      archive: archive !== null ? `${archive}` : null
    };
    return ApiEndpointsService.createUrlWithQueryParameters(
      Uris.JOBS.BASE_URI,
      (qs: QueryStringParameters) => {
        this.buildQueryStringFromFilters(objectParam, qs);
      });
  }

  getAllValidatedJobEndpoint(pageable: Pageable, criteria: JobCriteriaFilter, archive: any): string {
    // on merge les filtres et les attributs de pagination
    const objectParam = {
      ...criteria,
      rows: pageable.rows,
      page: pageable.page,
      sortBy: pageable.sortBy,
      sortOrder: pageable.sortOrder,
      archive: archive !== null ? `${archive}` : null
    };
    return ApiEndpointsService.createUrlWithQueryParameters(
      Uris.JOBS.GET_VALIDATED_JOBS,
      (qs: QueryStringParameters) => {
        this.buildQueryStringFromFilters(objectParam, qs);
      });
  }

  getJobByIdEndpoint(jobId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.JOBS.BASE_URI, [jobId]);
  }

  getLatestJobsEndpoint(jobsCount: number): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.JOBS.LATEST_JOBS, [jobsCount]);
  }

  createJobPostingEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.JOBS.BASE_URI);
  }

  updateJobPostingEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.JOBS.BASE_URI);
  }

  deleteJobPostingEndpoint(JobId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.JOBS.BASE_URI, [JobId]);
  }

  toggleArchiveJobsEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.JOBS.TOGGLE_ARCHIVE);
  }

  archiveJobPostingEndpoint(JobId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.JOBS.BASE_URI, [JobId, 'archive']);
  }

  getAllPendingActivationJobsEndpoint(pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.JOBS.GET_ALL_PENDING_JOBS, pageable);
  }

  getValidateJobEndpoint(jobId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.JOBS.BASE_URI, [jobId, 'validate']);
  }

  /**
   * Companies
   */

  getAllCompanyByCriteriaEndpoint(pageable: Pageable, criteria: Criteria): string {
    // on merge les filtres et les attributs de pagination
    const objectParam = ApiEndpointsService.concatCriterias(criteria, pageable);
    return ApiEndpointsService.createUrlWithQueryParameters(
      Uris.COMPANIES.BASE_URI,
      (qs: QueryStringParameters) =>
        this.buildQueryStringFromFilters(objectParam, qs)
    );
  }

  getAllCompanyEndpoint(pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.COMPANIES.BASE_URI, pageable);
  }

  createCompanyEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.COMPANIES.BASE_URI);
  }

  updateCompanyEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.COMPANIES.BASE_URI);
  }

  /**
   * Posts
   */

  getAllPostsEndpoint(pageable: Pageable): string {

    return ApiEndpointsService.createUrlWithPageable(Uris.POSTS.BASE_URI, pageable);
  }

  getPostEndpoint(postId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.POSTS.BASE_URI, [postId]);
  }

  getLatestPostsEndpoint(postsCount: number): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.POSTS.LATEST_POSTS, [postsCount]);
  }

  getPostCommentsEndpoint(pageable: any, postId: string): string {
    const uri = ApiEndpointsService.createUrlWithPathVariables(Uris.POSTS.BASE_URI, [postId, 'comments'])
      // createUrlWithPathVariables et createUrlWithPageable ajoutent le endpoint ('/api/') de l'api en début de l'adresse générée
      // on se retrouve donc avec une répétition en les chaînant. Il faut donc en supprimer un
      .replace(`${Constants.API_ENDPOINT}/`, '');

    return ApiEndpointsService.createUrlWithPageable(uri, pageable);
  }

  createPostEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.POSTS.BASE_URI);
  }

  /**
   * Comments
   */

  postCommentEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.COMMENTS.BASE_URI);
  }

  /**
   * Votes
   */

  getVoteEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.VOTES.BASE_URI);
  }

  /**
   * Groups
   */

  createGroupEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.GROUP.BASE_URI);
  }

  createGroupWithoutImageEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.GROUP.CREATE_NO_IMAGE);
  }

  findByTopPostingEndpoint(limit: number): string {
    return ApiEndpointsService.createUrlWithQueryParameters(
      Uris.GROUP.TOP_POSTING, (qs: QueryStringParameters) => qs.push('limit', limit)
    );
  }

  findAllActiveGroupsEndpoint(pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.GROUP.VALIDATED_GROUPS, pageable);
  }

  getValidateGroupEndpoint(groupName: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.GROUP.BASE_URI, [groupName, 'validate']);
  }

  getAllPendingGroupEndpoint(pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.GROUP.GET_ALL_PENDING_GROUPS, pageable);
  }

  createGroupSubscription(groupName: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.GROUP.BASE_URI, [groupName, 'subscribe']);
  }

  /**
   * JOB APPLICATION
   */

  createJobApplicationEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.JOB_APPLICATION.BASE_URI);
  }

  getAllApplicationForCurrentUserEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.JOB_APPLICATION.BASE_URI);
  }

  updateApplicationStatusEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.JOB_APPLICATION.BASE_URI);
  }

  deleteApplicationEndpoint(jobId: string): string {
    return ApiEndpointsService.createUrlWithQueryParameters(
      Uris.JOB_APPLICATION.BASE_URI,
      (qs: QueryStringParameters) => qs.push('jobId', jobId)
    );
  }

  /**
   * Ajoute les attributs filtrés d'un objet de paramétrage de requête à un QueryStringParameters
   * @return qs le QueryStringParameters modifié
   */
  private buildQueryStringFromFilters(filter: any, qs: QueryStringParameters): QueryStringParameters {
    Object.keys(filter)
      .filter(value => !!filter[value])
      .forEach(key => {
        if (filter[key] !== undefined) {
          qs.push(key, filter[key]);
        }
      });
    return qs;
  }
}
