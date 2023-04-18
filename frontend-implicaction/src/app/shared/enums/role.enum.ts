import {EnumCodeLabelAbstract} from './enum-code-label-abstract.enum';

export enum RoleEnumCode {
  ADMIN = 'ROLE_ADMIN',
  USER = 'ROLE_USER',
  PREMIUM = 'ROLE_PREMIUM'
}

export class RoleEnum extends EnumCodeLabelAbstract<RoleEnumCode> {
  static readonly ADMIN = new RoleEnum(RoleEnumCode.ADMIN, 'admin');
  static readonly USER = new RoleEnum(RoleEnumCode.USER, 'utilisateur');
  static readonly PREMIUM = new RoleEnum(RoleEnumCode.PREMIUM, 'premium');

  static all(): RoleEnum[] {
    return this.values();
  }

  static from(code: RoleEnumCode): RoleEnum {
    return this.fromCode(code);
  }
}
