import {RoleEnumCode} from './role.enum';

export class Univers {
  // l'ordre de déclaration de ces variables correspond à l’ordre d’affichage dans le menu
  static readonly HOME = new Univers('Accueil', '', true);
  static readonly COMPANY_AREA = new Univers('Espace entreprise', 'entreprise', true);
  static readonly COMMUNITY = new Univers('Communauté', 'community', true, [RoleEnumCode.USER]);
  static readonly FORUM = new Univers('Forum', 'forums', true, [RoleEnumCode.USER]);
  static readonly JOBS = new Univers(`Offres d'emploi`, 'jobs', true, [RoleEnumCode.USER]);
  static readonly BOARD = new Univers('Job Board', 'board', true, [RoleEnumCode.PREMIUM]);
  static readonly ADMIN = new Univers('Admin', 'admin', false, [RoleEnumCode.ADMIN]);
  static readonly AUTH = new Univers('Auth', 'auth', false);

  constructor(
    readonly title: string,
    readonly url: string,
    readonly isMenuItem: boolean,
    readonly roles?: RoleEnumCode[]
  ) {
  }

  static all(): Univers[] {
    return Object.values(this).filter(val => typeof val === 'object');
  }
}
