import {Injectable} from '@angular/core';
import {UrlBuilder} from '../../shared/classes/url-builder';
import {QueryStringParameters} from '../../shared/classes/query-string-parameters';
import {Constants} from '../../config/constants';
import {Uris} from '../../shared/models/uris';
import {Pageable} from '../../shared/models/pageable';
import {JobCriteriaFilter} from '../../job/models/job-criteria-filter';
import {Criteria} from '../../shared/models/criteria';
import {Response} from '../../forum/model/response';
import {GetCategoriesOptions} from '../../forum/services/category.service';
import {Profile} from '../../community/models/profile/profile';
import {Relation} from '../../community/models/relation/relation';

export type QueryStringHandler = (queryStringParameters: QueryStringParameters) => void;
export type CreateUrlOptions = { isMockApi?: boolean, queryStringHandler?: QueryStringHandler, pathVariables?: any[] };

@Injectable({
  providedIn: 'root'
})
@Injectable()
export class ApiEndpointsService {

  private static preparePathVariables(pathVariables?: CreateUrlOptions['pathVariables']) {
    let encodedPathVariablesUrl = '';
    if (pathVariables?.length > 0) {
      // Push extra path variables
      for (const pathVariable of pathVariables) {
        let pathVariableStr = pathVariable instanceof Array ? pathVariable.join(',') : pathVariable;

        if (pathVariable !== null) {
          encodedPathVariablesUrl += `/${encodeURIComponent(pathVariableStr.toString())}`;
        }
      }
    }
    return encodedPathVariablesUrl;
  }

  private static createUrlWithOptions(action: string, opts: CreateUrlOptions) {
    // path variables
    const encodedPathVariablesUrl = ApiEndpointsService.preparePathVariables(opts.pathVariables);

    const urlBuilder: UrlBuilder = new UrlBuilder(
      opts.isMockApi ? Constants.API_MOCK_ENDPOINT : Constants.API_ENDPOINT,
      action + encodedPathVariablesUrl
    );

    opts.queryStringHandler?.(urlBuilder.queryString);

    return urlBuilder.toString();
  }

  // URL
  private static createUrl(action: string, isMockApi = false): string {
    return ApiEndpointsService.createUrlWithOptions(action, {isMockApi});
  }


  // URL WITH QUERY PARAMETERS
  private static createUrlWithQueryParameters(
    action: string,
    queryStringHandler?:
      (queryStringParameters: QueryStringParameters) => void,
    isPublicEndpoint = false
  ): string {
    const urlBuilder: UrlBuilder = new UrlBuilder(
      isPublicEndpoint ? Constants.PUBLIC_ENDPOINT : Constants.API_ENDPOINT,
      action
    );
    // Push extra query string params
    if (queryStringHandler) {
      queryStringHandler(urlBuilder.queryString);
    }
    return urlBuilder.toString();
  }

  // URL WITH PATH VARIABLES
  private static createUrlWithPathVariables(action: string, pathVariables: any[] = [], isPublicEndpoint = false): string {
    let encodedPathVariablesUrl = '';
    // Push extra path variables
    for (const pathVariable of pathVariables) {
      if (pathVariable !== null) {
        encodedPathVariablesUrl += `/${encodeURIComponent(pathVariable.toString())}`;
      }
    }
    const urlBuilder: UrlBuilder = new UrlBuilder(isPublicEndpoint ? Constants.PUBLIC_ENDPOINT : Constants.API_ENDPOINT, `${action}${encodedPathVariablesUrl}`);
    return urlBuilder.toString();
  }

  private static createUrlWithPageable(uri: string, pageable: Pageable<any>): string {
    return ApiEndpointsService.createUrlWithQueryParameters(
      uri,
      (qs: QueryStringParameters) => {
        if (pageable.number) {
          qs.push('page', pageable.number);
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

  private static concatCriteria(criteria: Criteria, pageable: Pageable<any>): any {
    return {
      ...criteria,
      rows: pageable.rows,
      page: pageable.number,
      sortBy: pageable.sortBy,
      sortOrder: pageable.sortOrder
    };
  }

  buildUriFromTemplate(url: string, params: Map<string, string>): string {
    return Object.keys(params)
      .reduce((accu, key) => {
        return accu.replace(`{${key}}`, params.get(key));
      }, url);
  }

  /**
   * APP
   */
  getAppEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.APP.BASE_URI);
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

  /**
   * Profiles
   */
  getProfileByUsernameEndpoint(username: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.PROFILES.BY_USERNAME, [username]);
  }

  updateProfileEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.PROFILES.BASE_URI);
  }

  getAllProfilesEndpoint(pageable: Pageable<Profile>): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.PROFILES.BASE_URI, pageable);
  }

  getActivateUserEndpoint(username: string): string {
    return ApiEndpointsService.createUrl(Uris.AUTH.ACTIVATE_USER.replace('{username}', username));
  }

  updateImageProfileEndpoint(username: string): string {
    return ApiEndpointsService.createUrl(Uris.PROFILES.POST_AVATAR.replace('{username}', username));
  }

  /**
   * Users
   */

  getAllUserEndpoint(pageable: Pageable<any>): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.USERS.BASE_URI, pageable);
  }

  getAllPendingActivationUsersEndpoint(pageable: Pageable<any>): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.USERS.GET_ALL_PENDING_USERS, pageable);
  }


  /**
   * Relations
   */

  confirmRelationEndpoint(relationId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.RELATIONS.BASE_URI, [relationId, 'confirm']);
  }

  cancelRelationEndpoint(relationId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.RELATIONS.BASE_URI, [relationId]);
  }

  getAllRelationsByUsernameEndPoint(userId: string, pageable: Pageable<Relation>): string {
    const uri = ApiEndpointsService.createUrlWithPathVariables(Uris.RELATIONS.ALL_BY_USERNAME, [userId])
      // createUrlWithPathVariables et createUrlWithPageable ajoutent l’url de base ('/api/') de l’api au début de l’adresse générée,
      // on se retrouve donc avec une répétition en les chaînant. Il faut donc en supprimer un
      .replace(`${Constants.API_ENDPOINT}/`, '');

    return ApiEndpointsService.createUrlWithPageable(uri, pageable);
  }

  getAllRelationRequestsReceivedEndpoint(username: string, pageable: Pageable<Relation>): string {
    const uri = ApiEndpointsService.createUrlWithPathVariables(Uris.RELATIONS.GET_FRIEND_REQUEST_RECEIVED, [username, 'received'])
      // createUrlWithPathVariables et createUrlWithPageable ajoutent l’url de base ('/api/') de l’api en début de l’adresse générée,
      // on se retrouve donc avec une répétition en les chaînant. Il faut donc en supprimer un
      .replace(`${Constants.API_ENDPOINT}/`, '');
    return ApiEndpointsService.createUrlWithPageable(uri, pageable);
  }

  getAllRelationRequestSentEndPoint(username: string, pageable: Pageable<Relation>): string {
    const uri = ApiEndpointsService.createUrlWithPathVariables(Uris.RELATIONS.GET_FRIEND_REQUEST_SENT, [username, 'sent'])
      // createUrlWithPathVariables et createUrlWithPageable ajoutent l’url de base ('/api/') de l’api en début de l’adresse générée,
      // on se retrouve donc avec une répétition en les chaînant. Il faut donc en supprimer un
      .replace(`${Constants.API_ENDPOINT}/`, '');
    return ApiEndpointsService.createUrlWithPageable(uri, pageable);
  }

  getAllCommunity(pageable: Pageable<Relation>): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.RELATIONS.GET_ALL_COMMUNITY, pageable);
  }

  createRelationEndpoint(): string {
    return ApiEndpointsService.createUrl(Uris.RELATIONS.REQUEST);
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

  /**
   * Jobs
   */

  getAllJobEndpoint(pageable: Pageable<any>, criteria: JobCriteriaFilter, archive: boolean, checkApply: boolean): string {
    // on merge les filtres et les attributs de pagination
    const objectParam = {
      ...criteria,
      rows: pageable.rows,
      page: pageable.number,
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

  getAllValidatedJobEndpoint(pageable: Pageable<any>, criteria: JobCriteriaFilter, archive: any): string {
    // on merge les filtres et les attributs de pagination
    const objectParam = {
      ...criteria,
      rows: pageable.rows,
      page: pageable.number,
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

  getLatestJobsEndpoint(jobCount: number = undefined): string {
    return ApiEndpointsService.createUrlWithQueryParameters(
      Uris.JOBS.LATEST_JOBS,
      (qs: QueryStringParameters) => {
        if (jobCount) {
          qs.push('rows', jobCount);
        }
      },
      true);
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

  getAllPendingActivationJobsEndpoint(pageable: Pageable<any>): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.JOBS.GET_ALL_PENDING_JOBS, pageable);
  }

  getValidateJobEndpoint(jobId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.JOBS.BASE_URI, [jobId, 'validate']);
  }

  /**
   * Companies
   */

  getAllCompanyByCriteriaEndpoint(pageable: Pageable<any>, criteria: Criteria): string {
    // on merge les filtres et les attributs de pagination
    const objectParam = ApiEndpointsService.concatCriteria(criteria, pageable);
    return ApiEndpointsService.createUrlWithQueryParameters(
      Uris.COMPANIES.BASE_URI,
      (qs: QueryStringParameters) =>
        this.buildQueryStringFromFilters(objectParam, qs)
    );
  }

  getAllCompanyEndpoint(pageable: Pageable<any>): string {
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

  getAllPostsEndpoint(pageable: Pageable<any>): string {

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
      // createUrlWithPathVariables et createUrlWithPageable ajoutent l’endpoint ('/api/') de l’api en début de l’adresse générée,
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

  getAllGroupsByMemberUsername(username: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.USERS.GROUP_LIST, [username, 'groups']);
  }

  findAllActiveGroupsEndpoint(pageable: Pageable<any>): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.GROUP.VALIDATED_GROUPS, pageable);
  }

  getValidateGroupEndpoint(groupName: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.GROUP.BASE_URI, [groupName, 'validate']);
  }

  getAllPendingGroupEndpoint(pageable: Pageable<any>): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.GROUP.GET_ALL_PENDING_GROUPS, pageable);
  }

  createGroupSubscription(groupName: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.GROUP.BASE_URI, [groupName, 'subscribe']);
  }

  /**
   * FORUM CATEGORIES
   */

  getCategories(opts?: GetCategoriesOptions): string;
  getCategories(ids: number[], opts?: GetCategoriesOptions): string;
  getCategories(ids?: number[] | GetCategoriesOptions, opts?: GetCategoriesOptions): string {
    const options: GetCategoriesOptions | undefined = (ids instanceof Array ? opts : ids);
    const pathVariables = ids instanceof Array ? [ids] : [];
    return ApiEndpointsService.createUrlWithOptions(Uris.FORUM.CATEGORIES, {
      pathVariables,
      queryStringHandler(queryString) {
        if (options?.withRecentlyUpdatedTopic) {
          queryString.push('withRecentlyUpdatedTopic', options.withRecentlyUpdatedTopic);
        }
      }
    });
  }

  getRootCategories(): string {
    return ApiEndpointsService.createUrlWithQueryParameters(Uris.FORUM.CATEGORIES, (queryParams) => {
      queryParams.push('onlyRoot', true);
      return queryParams;
    });
  }

  getCategory(id: number): string {
    return this.getCategories([id]);
  }

  getCategoryTopics(id: number, pageable: Pageable<any>): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.FORUM.CATEGORIES_TOPICS(id), pageable);
  }

  createCategory(): string {
    return ApiEndpointsService.createUrl(Uris.FORUM.CATEGORIES);
  }

  deleteCategory(categoryId: number): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.FORUM.CATEGORIES, [categoryId]);
  }

  editCategory(): string {
    return ApiEndpointsService.createUrl(Uris.FORUM.CATEGORIES);
  }

  /**
   * FORUM TOPICS
   */

  getTopic(id: number): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.FORUM.TOPICS, [id]);
  }

  getLatestTopics(topicCount: number) {
    return ApiEndpointsService.createUrlWithQueryParameters(
      Uris.FORUM.LATEST_TOPICS,
      (queryParams) => {
        queryParams.push('rows', topicCount);
        return queryParams;
      },
      true
    );
  }

  getTopicResponses(id: number, pageable: Pageable<Response>): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.FORUM.TOPICS_RESPONSES(id), pageable);
  }

  createTopic(): string {
    return ApiEndpointsService.createUrl(Uris.FORUM.TOPICS);
  }

  editTopic(): string {
    return ApiEndpointsService.createUrl(Uris.FORUM.TOPICS);
  }

  deleteTopic(id: number): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.FORUM.TOPICS, [id]);
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
   * Ajoute les attributs filtrés d’un objet de paramétrage de requête à un QueryStringParameters
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
