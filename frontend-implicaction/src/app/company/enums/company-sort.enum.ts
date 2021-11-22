import {EnumCodeLabelAbstract} from '../../shared/enums/enum-code-label-abstract.enum';
import {SortDirectionEnum} from '../../shared/enums/sort-direction.enum';

export enum CompanySortEnumCode {
  NAME_ASC = 'NAME_ASC',
  NAME_DESC = 'NAME_DESC'
}

/**
 * Représente les différents paramètres de tri des compagnies
 */
export class CompanySortEnum extends EnumCodeLabelAbstract<CompanySortEnumCode> {
  static readonly NAME_DESC = new CompanySortEnum(
    CompanySortEnumCode.NAME_DESC,
    'Trier par ordre décroissant de l\'alphabet',
    'name',
    SortDirectionEnum.DESC
  );
  static readonly NAME_ASC = new CompanySortEnum(
    CompanySortEnumCode.NAME_ASC,
    'Trier par ordre alphabétique ',
    'name',
    SortDirectionEnum.ASC
  );

  /**
   * @param code code associé à la constante de tri
   * @param label label à afficher (ex: 'Nom de la compagnie par ordre alphabétique')
   * @param sortBy correspond au nom de l'attribut du modèle dans le back (ex: company.name permet de trier par nom de compagnie)
   * @param sortDirection ASC pour tri ascendant, DESC pour descendant
   */
  constructor(
    readonly code: CompanySortEnumCode,
    readonly label: string,
    readonly sortBy: string,
    readonly sortDirection: SortDirectionEnum
  ) {
    super(code, label);
  }

  static all(): CompanySortEnumCode[] {
    return this.values();
  }

  static from(code: CompanySortEnumCode): CompanySortEnum {
    return this.fromCode(code);
  }
}
