import {Company} from './company';
import {ContractEnum} from '../enums/contract.enum';
import {StatusEnum} from '../enums/status.enum';

export interface JobPosting {
  id?: string;
  keywords?: string;
  location?: string;
  salary?: string;
  title?: string;
  shortDescription?: string;
  description?: string;
  createdAt?: Date;
  contractType?: ContractEnum;
  company?: Company;
  status?: StatusEnum;
}
