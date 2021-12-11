import {EnumCodeLabelAbstract} from './enum-code-label-abstract.enum';

export enum BusinessSectorEnumCode {
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

export class BusinessSectorEnum extends EnumCodeLabelAbstract<BusinessSectorEnumCode> {
  static readonly AGROALIMENTAIRE = new BusinessSectorEnum(BusinessSectorEnumCode.AGROALIMENTAIRE, 'Agroalimentaire');
  static readonly ASSURANCE = new BusinessSectorEnum(BusinessSectorEnumCode.ASSURANCE, 'Assurance');
  static readonly AUTOMOBILE = new BusinessSectorEnum(BusinessSectorEnumCode.AUTOMOBILE, 'Automobile');
  static readonly BANQUE = new BusinessSectorEnum(BusinessSectorEnumCode.BANQUE, 'Banque');
  static readonly BTP = new BusinessSectorEnum(BusinessSectorEnumCode.BTP, 'BTP');
  static readonly CHIMIE = new BusinessSectorEnum(BusinessSectorEnumCode.CHIMIE, 'Chimie');
  static readonly COMMERCE = new BusinessSectorEnum(BusinessSectorEnumCode.COMMERCE, 'Commerce');
  static readonly COMMUNICATION = new BusinessSectorEnum(BusinessSectorEnumCode.COMMUNICATION, 'Communication');
  static readonly EDITION = new BusinessSectorEnum(BusinessSectorEnumCode.EDITION, 'Édition');
  static readonly ELECTRONIQUE = new BusinessSectorEnum(BusinessSectorEnumCode.ELECTRONIQUE, 'Éléctronique');
  static readonly ETUDES_ET_CONSEILS = new BusinessSectorEnum(BusinessSectorEnumCode.ETUDES_ET_CONSEILS, 'Études et conseils');
  static readonly IMPRIMERIE = new BusinessSectorEnum(BusinessSectorEnumCode.IMPRIMERIE, 'Imprimerie');
  static readonly INDUSTRIE_PHARMACEUTIQUE = new BusinessSectorEnum(BusinessSectorEnumCode.INDUSTRIE_PHARMACEUTIQUE, 'Industrie pharmaceutique');
  static readonly INFORMATIQUE = new BusinessSectorEnum(BusinessSectorEnumCode.INFORMATIQUE, 'Informatique');
  static readonly LOGISTIQUE = new BusinessSectorEnum(BusinessSectorEnumCode.LOGISTIQUE, 'Logistique');
  static readonly METALLURGIE = new BusinessSectorEnum(BusinessSectorEnumCode.METALLURGIE, 'Métallurgie');
  static readonly SERVICES_AUX_ENTREPRISES = new BusinessSectorEnum(BusinessSectorEnumCode.SERVICES_AUX_ENTREPRISES, 'Services aux entreprises');
  static readonly TELECOMS = new BusinessSectorEnum(BusinessSectorEnumCode.TELECOMS, 'Télécoms');
  static readonly TRANSPORTS = new BusinessSectorEnum(BusinessSectorEnumCode.TRANSPORTS, 'Transports');

  constructor(
    readonly code: BusinessSectorEnumCode,
    readonly label: string
  ) {
    super(code, label);
  }

  static all(): BusinessSectorEnum[] {
    return this.values();
  }

  static from(code: BusinessSectorEnumCode): BusinessSectorEnum {
    return this.fromCode(code);
  }
}
