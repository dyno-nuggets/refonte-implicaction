import {EnumCodeLabelAbstract} from '../../shared/enums/enum-code-label-abstract.enum';

export enum ApplyStatusCode {
  PENDING = 'PENDING',
  SENT = 'SENT',
  CHASED = 'CHASED',
  INTERVIEW = 'INTERVIEW',
  HIRED = 'HIRED',
  REJECTED = 'REJECTED',
  CANCELED = 'CANCELED',
}

export class ApplyStatusEnum extends EnumCodeLabelAbstract<ApplyStatusCode> {
  static readonly PENDING = new EnumCodeLabelAbstract(ApplyStatusCode.PENDING, 'Je vais postuler');
  static readonly SENT = new EnumCodeLabelAbstract(ApplyStatusCode.SENT, `J'ai postulé`);
  static readonly CHASED = new EnumCodeLabelAbstract(ApplyStatusCode.CHASED, `J'ai relancé`);
  static readonly INTERVIEW = new EnumCodeLabelAbstract(ApplyStatusCode.INTERVIEW, `J'ai un entretien`);

  constructor(
    readonly code: ApplyStatusCode,
    readonly label: string
  ) {
    super(code, label);
  }

  static all(): ApplyStatusEnum[] {
    return this.values();
  }

  static from(code: ApplyStatusCode): ApplyStatusEnum {
    return this.fromCode(code);
  }
}
