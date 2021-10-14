import {ContractTypeCode} from './contract-type-enum';

export interface JobCriteriaFilter {
  search?: string;
  contractType?: ContractTypeCode;
}
