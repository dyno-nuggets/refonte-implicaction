import {EnumCodeLabelAbstract} from "../../../shared/enums/enum-code-label-abstract.enum";
import {Univers} from "../../../shared/enums/univers";

export enum ProfileMenuCode {
  ALL = 'ALL',
  ALL_FRIENDS = 'ALL_FRIENDS',
  ONLY_FRIEND_REQUESTS = 'ONLY_FRIEND_REQUESTS'
}

export class ProfileMenuEnum extends EnumCodeLabelAbstract<ProfileMenuCode> {

  static readonly ALL = new ProfileMenuEnum(ProfileMenuCode.ALL, 'Tous les utilisateurs', null, `/${Univers.COMMUNITY.url}/profiles`, null);
  static readonly ALL_FRIENDS = new ProfileMenuEnum(ProfileMenuCode.ALL_FRIENDS, 'Mes amis', null, `/${Univers.COMMUNITY.url}/profiles`, ProfileMenuCode.ALL_FRIENDS);
  static readonly ONLY_FRIEND_REQUESTS = new ProfileMenuEnum(ProfileMenuCode.ONLY_FRIEND_REQUESTS, 'Invitations', null, `/${Univers.COMMUNITY.url}/profiles`, ProfileMenuCode.ONLY_FRIEND_REQUESTS);

  constructor(readonly code: ProfileMenuCode, readonly label: string, readonly icon: string, readonly url: string, readonly filter: ProfileMenuCode) {
    super(code, label);
  }
}
