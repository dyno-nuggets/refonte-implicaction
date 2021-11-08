import {RoleEnumCode} from './role.enum';

export class Univers {
  static readonly HOME = new Univers('Accueil', '');
  static readonly USERS = new Univers('Communauté', 'users', [RoleEnumCode.USER]);
  static readonly JOBS = new Univers(`Offres d'emploi`, 'jobs', [RoleEnumCode.USER]);
  static readonly DISCUSSION = new Univers('Discussion', 'discussion', [RoleEnumCode.USER]);
  static readonly ADMIN = new Univers('Admin', 'admin', [RoleEnumCode.ADMIN]);

  constructor(
    readonly title: string,
    readonly url: string,
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
