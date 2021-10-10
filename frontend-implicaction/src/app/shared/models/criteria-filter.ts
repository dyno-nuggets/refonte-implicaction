import {ContractTypeCode} from '../../job/models/contract-type-enum';

export interface CriteriaFilter {
  search?: string;
  contractType?: ContractTypeCode;
}
