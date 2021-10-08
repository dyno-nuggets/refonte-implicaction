import {EnumCodeLabelAbstract} from '../../shared/enums/enum-code-label-abstract.enum';

export enum JobSortEnumCode {
  CONTRACT_TYPE = 'CONTRACT_TYPE',
  DATE_ASC = 'DATE_ASC',
  DATE_DESC = 'DATE_DESC'
}

export class JobSortEnum extends EnumCodeLabelAbstract<JobSortEnumCode> {
  static readonly DATE_DESC = new JobSortEnum(JobSortEnumCode.DATE_DESC, 'Les plus récents', 'createdAt', 'DESC');
  static readonly DATE_ASC = new JobSortEnum(JobSortEnumCode.DATE_ASC, 'Les plus anciens', 'createdAt', 'ASC');

  /**
   * Représente
   * @param code code associé à la constante de tri
   * @param label label à afficher
   * @param sortBy nom de la colonne en base de données
   * @param sortOrder ASC pour tri ascendant, DESC pour descendant
   */
  constructor(readonly code: JobSortEnumCode, readonly label: string, readonly sortBy: string, readonly sortOrder: string) {
    super(code, label);
  }

  static all(): JobSortEnumCode[] {
    return this.values();
  }

  static from(code: JobSortEnumCode): JobSortEnum {
    return this.fromCode(code);
  }
}
