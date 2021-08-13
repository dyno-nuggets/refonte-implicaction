import {QueryStringParameters} from './query-string-parameters';

/**
 * Cette classe utilise la classe QueryStringParameters class pour générer l'url final de l'api:
 * ex: https://domain.com/api/get-user?id=3&type=customer.
 * Cette url est une combinaison de 3 parties :
 * <ol>
 *   <li>l'url de l'api (https://domain.com)</li>
 *   <li>l'action (get-user)</li>
 *   <li>la query string (id=3&type=customer)</li>
 * </ol>
 */
export class UrlBuilder {
  public url: string;
  public queryString: QueryStringParameters;

  constructor(private baseUrl: string, private action: string, queryString?: QueryStringParameters) {
    this.url = [baseUrl, action].join('/');
    this.queryString = queryString || new QueryStringParameters();
  }

  public toString(): string {
    const queryString: string = this.queryString ?
      this.queryString.toString() : '';
    return queryString ? `${this.url}?${queryString}` : this.url;
  }
}
