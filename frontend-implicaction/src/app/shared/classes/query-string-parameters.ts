/**
 * Cette classe reçoit une paire clé-valeur de chaîne de requête et encode la valeur. Elle retourne également toutes les
 * paires clé-valeur dans une chaîne avec le caractère &: par exemple, id=3&type=customer.
 */
export class QueryStringParameters {
  private paramsAndValues: string[];

  constructor() {
    this.paramsAndValues = [];
  }

  // tslint:disable-next-line:ban-types
  public push(key: string, value: Object): void {
    value = encodeURIComponent(value.toString());
    this.paramsAndValues.push([key, value].join('='));
  }

  public toString = (): string => this.paramsAndValues.join('&');
}
