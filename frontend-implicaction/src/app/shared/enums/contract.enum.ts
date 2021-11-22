import {EnumCodeLabelAbstract} from './enum-code-label-abstract.enum';

export enum ContractEnumCode {
  CDD = 'CDD',
  CDI = 'CDI',
  INTERIM = 'INTERIM',
  ALTERN = 'ALTERN'
}

export class ContractEnum extends EnumCodeLabelAbstract<ContractEnumCode> {
  static readonly CDI = new ContractEnum(ContractEnumCode.CDI, 'CDI');
  static readonly CDD = new ContractEnum(ContractEnumCode.CDD, 'CDD');
  static readonly INTERIM = new ContractEnum(ContractEnumCode.INTERIM, 'Intérim');
  static readonly ALTERN = new ContractEnum(ContractEnumCode.ALTERN, 'Alternance');

  constructor(
    readonly code: ContractEnumCode,
    readonly label: string
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
