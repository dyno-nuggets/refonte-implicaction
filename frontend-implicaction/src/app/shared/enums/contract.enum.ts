import {EnumCodeLabelAbstract} from './enum-code-label-abstract.enum';

export enum ContractEnumCode {
  CDD = 'CDD',
  CDI = 'CDI'
}

export class ContractEnum extends EnumCodeLabelAbstract<ContractEnumCode> {
  static readonly CDI = new ContractEnum(ContractEnumCode.CDI, 'CDI', 1);
  static readonly CDD = new ContractEnum(ContractEnumCode.CDD, 'CDD', 2);

  constructor(
    readonly code: ContractEnumCode,
    readonly label: string,
    readonly id: number
  ) {
    super(code, label);
  }

  static all(): ContractEnum[] {
    return this.values();
  }

  static from(code: ContractEnumCode): ContractEnum {
    return this.fromCode(code);
  }
}
