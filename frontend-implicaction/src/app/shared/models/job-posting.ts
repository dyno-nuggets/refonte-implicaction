import {Status} from './status';
import {Company} from './company';
import {ContractType} from './contractType';

export interface JobPosting {
  id?: string;
  keywords?: string;
  location?: string;
  salary?: string;
  title?: string;
  description?: string;
  createdAt?: Date;
  contractType?: ContractType;
  company?: Company;
  status?: Status;
}
