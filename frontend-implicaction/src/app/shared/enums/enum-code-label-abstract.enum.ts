/**
 * Cette classe est une classe mère pour les classes avec le pattern enum pour type/label
 */
export class EnumCodeLabelAbstract<T> {

  constructor(readonly code: T, readonly label: string) {
  }

  /**
   * @return toutes les valeurs de l'enum
   */
  protected static values<U>(): U[] {
    return Object.values(this).filter(enumVal => typeof enumVal === 'object');
  }

  /**
   * @return l'enum correspondant au code fourni
   */
  protected static fromCode<U extends EnumCodeLabelAbstract<R>, R = {}>(code: R): U {
    return this.values<U>().find(enumVal => enumVal.code === code);
  }
}
