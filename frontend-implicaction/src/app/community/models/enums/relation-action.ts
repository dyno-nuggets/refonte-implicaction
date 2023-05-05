import {EnumCodeLabelAbstract} from '../../../shared/enums/enum-code-label-abstract.enum';

export enum RelationActionEnumCode {
  CREATE = 'CREATE',
  DELETE = 'DELETE',
  CONFIRM = 'CONFIRM'
}

export class RelationActionEnum extends EnumCodeLabelAbstract<RelationActionEnumCode> {

  public static readonly CREATE = new RelationActionEnum(RelationActionEnumCode.CREATE, 'ajouter comme ami(e)', 'fas fa-user-plus', 'btn btn-primary btn-sm me-1');
  public static readonly DELETE = new RelationActionEnum(RelationActionEnumCode.DELETE, 'supprimer la relation', 'fas fa-user-times', 'btn btn-secondary btn-sm me-1');
  public static readonly CONFIRM = new RelationActionEnum(RelationActionEnumCode.CONFIRM, 'confirmer la relation', 'fas fa-user-check', 'btn btn-primary btn-sm me-1');

  private constructor(readonly code: RelationActionEnumCode, readonly label: string, readonly icon: string, readonly styleClass: string) {
    super(code, label);
  }
}
