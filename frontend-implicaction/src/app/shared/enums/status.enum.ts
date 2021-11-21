import {EnumCodeLabelAbstract} from './enum-code-label-abstract.enum';

export enum StatusEnumCode {}

export class StatusEnum extends EnumCodeLabelAbstract<StatusEnumCode> {

  constructor(
    readonly code: StatusEnumCode,
    readonly label: string,
    readonly type: string,
    readonly id: number
  ) {
    super(code, label);
  }

  static all(): StatusEnum[] {
    return this.values();
  }

  static from(code: StatusEnumCode): StatusEnum {
    return this.fromCode(code);
  }
}
