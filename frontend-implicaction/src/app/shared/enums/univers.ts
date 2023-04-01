import {RoleEnumCode} from './role.enum';

export class Univers {
  // l'ordre de déclaration de ces variables correspond à l'ordre d'affichage dans le menu
  static readonly HOME = new Univers('Accueil', '', true);
  static readonly COMPANY_AREA = new Univers('Espace entreprise', 'entreprise', true);
  static readonly PROFILE = new Univers('Profil', 'profiles', false, [RoleEnumCode.USER]);
  static readonly COMMUNITY = new Univers('Communauté', 'community', true, [RoleEnumCode.USER]);
  static readonly JOBS = new Univers(`Offres d'emploi`, 'jobs', true, [RoleEnumCode.USER]);
  static readonly BOARD = new Univers('Job Board', 'board', true, [RoleEnumCode.PREMIUM]);
  static readonly DISCUSSIONS = new Univers('Discussions', 'old_forum', true, [RoleEnumCode.USER]);
  static readonly ADMIN = new Univers('Admin', 'admin', false, [RoleEnumCode.ADMIN]);
  static readonly FORUM = new Univers('Forum', 'forum', [RoleEnumCode.USER]);

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

  /**
   * Récupère l'Univers correspondant à l'url en paramètres
   */
  static fromUrl(url: string): Univers {
    const rootUrl = url.split('/', 2)[1]; // récupère la racine de l'url (ex: users si /users/1)
    return Univers.all().find(univers => rootUrl.startsWith(univers.url));
  }

  static getAllowedUnivers(roles: RoleEnumCode[] = []): Univers[] {
    return Univers.all()
      .filter(univers => !univers.roles || univers.roles.filter(roleAllowed => roles.includes(roleAllowed)).length > 0);
  }
}
