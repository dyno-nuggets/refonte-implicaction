import {Injectable} from '@angular/core';
import {UrlBuilder} from '../../shared/classes/url-builder';
import {QueryStringParameters} from '../../shared/classes/query-string-parameters';
import {Constants} from '../../config/constants';
import {Uris} from '../../shared/models/uris';

@Injectable({
  providedIn: 'root'
})
@Injectable()
export class ApiEndpointsService {

  constructor(private constants: Constants) {
  }

  getLoginEndpoint(): string {
    return this.createUrl(Uris.AUTH.LOGIN);
  }

  getSignUpEndpoint(): string {
    return this.createUrl(Uris.AUTH.SIGNUP);
  }

  getJwtRefreshTokenEndpoint(): string {
    return this.createUrl(Uris.AUTH.REFRESH_TOKEN);
  }

  getLogoutEndpoint(): string {
    return this.createUrl(Uris.AUTH.LOGOUT);
  }

  // URL
  private createUrl(action: string, isMockAPI: boolean = false): string {
    const urlBuilder: UrlBuilder = new UrlBuilder(
      isMockAPI ? this.constants.API_MOCK_ENDPOINT : this.constants.API_ENDPOINT,
      action
    );
    return urlBuilder.toString();
  }

  private createUrlWithQueryParameters(
    action: string,
    queryStringHandler?:
      (queryStringParameters: QueryStringParameters) => void
  ): string {
    const urlBuilder: UrlBuilder = new UrlBuilder(
      this.constants.API_ENDPOINT,
      action
    );
    // Push extra query string params
    if (queryStringHandler) {
      queryStringHandler(urlBuilder.queryString);
    }
    return urlBuilder.toString();
  }

  // URL WITH PATH VARIABLES
  private createUrlWithPathVariables(action: string, pathVariables: any[] = []): string {
    let encodedPathVariablesUrl = '';
    // Push extra path variables
    for (const pathVariable of pathVariables) {
      if (pathVariable !== null) {
        encodedPathVariablesUrl += `/${encodeURIComponent(pathVariable.toString())}`;
      }
    }
    const urlBuilder: UrlBuilder = new UrlBuilder(this.constants.API_ENDPOINT, `${action}${encodedPathVariablesUrl}`);
    return urlBuilder.toString();
  }
}
