import {Company} from './company';
import {ContractEnumCode} from '../enums/contract.enum';
import {BusinessSectorEnumCode} from '../enums/sector.enum';

export interface JobPosting {
  id?: string;
  keywords?: string;
  location?: string;
  salary?: string;
  title?: string;
  shortDescription?: string;
  description?: string;
  createdAt?: Date;
  contractType?: ContractEnumCode;
  businessSector?: BusinessSectorEnumCode;
  company?: Company;
  apply?: boolean;
  archive?: boolean;
  valid?: boolean;
  posterId?: string;
  posterName?: string;
}
