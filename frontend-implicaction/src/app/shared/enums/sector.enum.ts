import {EnumCodeLabelAbstract} from './enum-code-label-abstract.enum';

export enum SectorEnumCode {
  AGROALIMENTAIRE = 'AGROALIMENTAIRE',
  ASSURANCE = 'ASSURANCE',
  AUTOMOBILE = 'AUTOMOBILE',
  BANQUE = 'BANQUE',
  BTP = 'BTP',
  CHIMIE = 'CHIMIE',
  COMMERCE = 'COMMERCE',
  COMMUNICATION = 'COMMUNICATION',
  EDITION = 'EDITION',
  ELECTRONIQUE = 'ELECTRONIQUE',
  ETUDES_ET_CONSEILS = 'ETUDES_ET_CONSEILS',
  IMPRIMERIE = 'IMPRIMERIE',
  INDUSTRIE_PHARMACEUTIQUE = 'INDUSTRIE_PHARMACEUTIQUE',
  INFORMATIQUE = 'INFORMATIQUE',
  LOGISTIQUE = 'LOGISTIQUE',
  METALLURGIE = 'METALLURGIE',
  SERVICES_AUX_ENTREPRISES = 'SERVICES_AUX_ENTREPRISES',
  TELECOMS = 'TELECOMS',
  TEXTILE = 'TEXTILE',
  TRANSPORTS = 'TRANSPORTS',

}

export class SectorEnum extends EnumCodeLabelAbstract<SectorEnumCode> {
  static readonly AGROALIMENTAIRE = new SectorEnum(SectorEnumCode.AGROALIMENTAIRE, 'Agroalimentaire');
  static readonly ASSURANCE = new SectorEnum(SectorEnumCode.ASSURANCE, 'Assurance');
  static readonly AUTOMOBILE = new SectorEnum(SectorEnumCode.AUTOMOBILE, 'Automobile');
  static readonly BANQUE = new SectorEnum(SectorEnumCode.BANQUE, 'Banque');
  static readonly BTP = new SectorEnum(SectorEnumCode.BTP, 'BTP');
  static readonly CHIMIE = new SectorEnum(SectorEnumCode.CHIMIE, 'Chimie');
  static readonly COMMERCE = new SectorEnum(SectorEnumCode.COMMERCE, 'Commerce');
  static readonly COMMUNICATION = new SectorEnum(SectorEnumCode.COMMUNICATION, 'Communication');
  static readonly EDITION = new SectorEnum(SectorEnumCode.EDITION, 'Édition');
  static readonly ELECTRONIQUE = new SectorEnum(SectorEnumCode.ELECTRONIQUE, 'Éléctronique');
  static readonly ETUDES_ET_CONSEILS = new SectorEnum(SectorEnumCode.ETUDES_ET_CONSEILS, 'Études et conseils');
  static readonly IMPRIMERIE = new SectorEnum(SectorEnumCode.IMPRIMERIE, 'Imprimerie');
  static readonly INDUSTRIE_PHARMACEUTIQUE = new SectorEnum(SectorEnumCode.INDUSTRIE_PHARMACEUTIQUE, 'Industrie pharmaceutique');
  static readonly INFORMATIQUE = new SectorEnum(SectorEnumCode.INFORMATIQUE, 'Informatique');
  static readonly LOGISTIQUE = new SectorEnum(SectorEnumCode.LOGISTIQUE, 'Logistique');
  static readonly METALLURGIE = new SectorEnum(SectorEnumCode.METALLURGIE, 'Métallurgie');
  static readonly SERVICES_AUX_ENTREPRISES = new SectorEnum(SectorEnumCode.SERVICES_AUX_ENTREPRISES, 'Services aux entreprises');
  static readonly TELECOMS = new SectorEnum(SectorEnumCode.TELECOMS, 'Télécoms');
  static readonly TRANSPORTS = new SectorEnum(SectorEnumCode.TRANSPORTS, 'Transports');

  constructor(
    readonly code: SectorEnumCode,
    readonly label: string
  ) {
    super(code, label);
  }

  static all(): SectorEnum[] {
    return this.values();
  }

  static from(code: SectorEnumCode): SectorEnum {
    return this.fromCode(code);
  }
}
