import {RoleEnumCode} from "../enums/role.enum";

export interface Principal {
  username?: string,
  roles?: RoleEnumCode[]
}
