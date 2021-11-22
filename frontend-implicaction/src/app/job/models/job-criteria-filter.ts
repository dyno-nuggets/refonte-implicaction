import {ContractEnumCode} from '../../shared/enums/contract.enum';

export interface JobCriteriaFilter {
  search?: string;
  contractType?: ContractEnumCode;
}
