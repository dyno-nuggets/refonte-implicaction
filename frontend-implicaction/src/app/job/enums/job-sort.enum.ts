import {EnumCodeLabelAbstract} from '../../shared/enums/enum-code-label-abstract.enum';
import {SortDirectionEnum} from '../../shared/enums/sort-direction.enum';

export enum JobSortEnumCode {
  CONTRACT_TYPE = 'CONTRACT_TYPE',
  DATE_ASC = 'DATE_ASC',
  DATE_DESC = 'DATE_DESC'
}

/**
 * Représente les différents paramètres de tri des jobs
 */
export class JobSortEnum extends EnumCodeLabelAbstract<JobSortEnumCode> {
  static readonly DATE_DESC = new JobSortEnum(JobSortEnumCode.DATE_DESC, 'Les plus récentes', 'createdAt', SortDirectionEnum.DESC);
  static readonly DATE_ASC = new JobSortEnum(JobSortEnumCode.DATE_ASC, 'Les plus anciennes', 'createdAt', SortDirectionEnum.ASC);

  /**
   * @param code code associé à la constante de tri
   * @param label label à afficher (ex: 'Nom de la compagnie par ordre alphabétique')
   * @param sortBy correspond au nom de l'attribut du modèle dans le back (ex: company.name permet de trier par nom de compagnie)
   * @param sortDirection ASC pour tri ascendant, DESC pour descendant
   */
  constructor(
    readonly code: JobSortEnumCode,
    readonly label: string,
    readonly sortBy: string,
    readonly sortDirection: SortDirectionEnum) {
    super(code, label);
  }

  static all(): JobSortEnumCode[] {
    return this.values();
  }

  static from(code: JobSortEnumCode): JobSortEnum {
    return this.fromCode(code);
  }
}
