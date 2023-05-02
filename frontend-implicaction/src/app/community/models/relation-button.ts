import {Relation} from './relation';
import {RelationActionEnum} from './enums/relation-action';

export interface RelationButton {
  relation: Relation;
  action: RelationActionEnum;
}
