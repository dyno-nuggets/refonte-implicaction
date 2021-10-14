import {EnumCodeLabelAbstract} from '../../shared/enums/enum-code-label-abstract.enum';

export enum ContractTypeCode {
  CDD = 'CDD',
  CDI = 'CDI'
}

export class ContractTypeEnum extends EnumCodeLabelAbstract<ContractTypeCode> {

  static readonly CDD = new ContractTypeEnum(ContractTypeCode.CDD, 'CDD');
  static readonly CDI = new ContractTypeEnum(ContractTypeCode.CDI, 'CDI');

  static all(): ContractTypeEnum[] {
    return this.values();
  }

  static from(code: ContractTypeCode): ContractTypeEnum {
    return this.fromCode(code);
  }
}
