import {EnumCodeLabelAbstract} from '../../shared/enums/enum-code-label-abstract.enum';

export enum JobStatusCode {
  PENDING, SENT, CHASED, INTERVIEW
}

export class JobStatus extends EnumCodeLabelAbstract<JobStatusCode> {
  static readonly PENDING = new EnumCodeLabelAbstract(JobStatusCode.PENDING, 'Je vais postuler');
  static readonly SENT = new EnumCodeLabelAbstract(JobStatusCode.SENT, `J'ai postulé`);
  static readonly CHASED = new EnumCodeLabelAbstract(JobStatusCode.CHASED, `J'ai relancé`);
  static readonly INTERVIEW = new EnumCodeLabelAbstract(JobStatusCode.INTERVIEW, `J'ai un entretien`);

  constructor(
    readonly code: JobStatusCode,
    readonly label: string
  ) {
    super(code, label);
  }

  static all(): JobStatus[] {
    return this.values();
  }

  static from(code: JobStatusCode): JobStatus {
    return this.fromCode(code);
  }
}
