import {Injectable} from '@angular/core';
import {UrlBuilder} from '../../shared/classes/url-builder';
import {QueryStringParameters} from '../../shared/classes/query-string-parameters';
import {Constants} from '../../config/constants';
import {Uris} from '../../shared/models/uris';
import {Pageable} from '../../shared/models/pageable';

@Injectable({
  providedIn: 'root'
})
@Injectable()
export class ApiEndpointsService {

  constructor() {
  }

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
        qs.push('page', pageable.page);
        qs.push('size', pageable.size);
      });
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

  /**
   * Users
   */

  getAllUserEndpoint(pageable: Pageable): string {
    return ApiEndpointsService.createUrlWithPageable(Uris.USERS.BASE_URI, pageable);
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

  updatePersonalInfoByUserIdEndpoint(userId: string): string {
    return ApiEndpointsService.createUrlWithPathVariables(Uris.USERS.BASE_URI, [userId, 'infos']);
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
}
