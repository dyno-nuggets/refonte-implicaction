import {EnumCodeLabelAbstract} from './enum-code-label-abstract.enum';

export enum StatusEnumCode {
  JOB_AVAILABLE = 'jobAvailable',
}

export class StatusEnum extends EnumCodeLabelAbstract<StatusEnumCode> {
  static readonly JOB_AVAILABLE = new StatusEnum(StatusEnumCode.JOB_AVAILABLE, 'jobAvailable', 'job_posting', 1);

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
