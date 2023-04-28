import {Relation} from "./relation";
import {EnumCodeLabelAbstract} from "../../../shared/enums/enum-code-label-abstract.enum";

export enum RelationActionEnumCode {
  CREATE = 'CREATE', DELETE = 'DELETE', CONFIRM = 'CONFIRM'
}

export interface RelationAction {
  relation: Relation;
  action: RelationActionEnumCode;
}

export class RelationActionEnum extends EnumCodeLabelAbstract<RelationActionEnumCode> {

  public static readonly CREATE = new RelationActionEnum(RelationActionEnumCode.CREATE, 'ajouter comme ami(e)');
  public static readonly DELETE = new RelationActionEnum(RelationActionEnumCode.DELETE, 'supprimer');
  public static readonly CONFIRM = new RelationActionEnum(RelationActionEnumCode.CONFIRM, 'confirmer');
}
