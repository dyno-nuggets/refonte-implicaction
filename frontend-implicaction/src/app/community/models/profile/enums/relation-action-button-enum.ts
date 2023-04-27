import {EnumCodeLabelAbstract} from "../../../../shared/enums/enum-code-label-abstract.enum";

export enum RelationActionButton {
  RELATION_DELETE = 'RELATION_CANCEL',
  RELATION_CREATE = 'RELATION_CREATE',
  RELATION_CONFIRM = 'RELATION_CONFIRM'
}


export class RelationActionButtonEnum implements EnumCodeLabelAbstract<RelationActionButton> {

  public static readonly RELATION_DELETE = new EnumCodeLabelAbstract(RelationActionButton.RELATION_DELETE, 'Supprimer');
  public static readonly RELATION_CREATE = new EnumCodeLabelAbstract(RelationActionButton.RELATION_CREATE, 'Ajouter comme ami(e)');
  public static readonly RELATION_CONFIRM = new EnumCodeLabelAbstract(RelationActionButton.RELATION_CONFIRM, 'Accepter la demande');

  constructor(readonly code: RelationActionButton, readonly label: string) {
  }
}
