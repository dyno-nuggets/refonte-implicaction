import { EnumCodeLabelAbstract } from 'src/app/shared/enums/enum-code-label-abstract.enum';

export enum ForumTableTypeCode {
  FORUM = 'FORUM',
  POST = 'POST',
}

export class ForumTableTypesEnum extends EnumCodeLabelAbstract<ForumTableTypeCode> {
  static readonly FORUM = new ForumTableTypesEnum(
    ForumTableTypeCode.FORUM,
    'Forums'
  );

  static readonly POST = new ForumTableTypesEnum(
    ForumTableTypeCode.POST,
    'Posts'
  );

  constructor(readonly code: ForumTableTypeCode, readonly label: string) {
    super(code, label);
  }

  static all(): ForumTableTypesEnum[] {
    return this.values();
  }

  static from(code: ForumTableTypeCode): ForumTableTypesEnum {
    return this.fromCode(code);
  }
}
