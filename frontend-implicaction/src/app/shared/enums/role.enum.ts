import {EnumCodeLabelAbstract} from './enum-code-label-abstract.enum';

export enum RoleEnumCode {
  ADMIN = 'ROLE_ADMIN',
  USER = 'ROLE_USER',
  JOB_SEEKER = 'ROLE_JOB_SEEKER',
  RECRUITER = 'ROLE_RECRUITER',
  PREMIUM = 'ROLE_PREMIUM'
}

export class RoleEnum extends EnumCodeLabelAbstract<RoleEnumCode> {
  static readonly ADMIN = new RoleEnum(RoleEnumCode.ADMIN, 'admin');
  static readonly USER = new RoleEnum(RoleEnumCode.USER, 'utilisateur');
  static readonly JOB_SEEKER = new RoleEnum(RoleEnumCode.JOB_SEEKER, 'en recherche');
  static readonly RECRUITER = new RoleEnum(RoleEnumCode.RECRUITER, 'recruteur');
  static readonly PREMIUM = new RoleEnum(RoleEnumCode.PREMIUM, 'premium');

  static all(): RoleEnum[] {
    return this.values();
  }

  static from(code: RoleEnumCode): RoleEnum {
    return this.fromCode(code);
  }
}
