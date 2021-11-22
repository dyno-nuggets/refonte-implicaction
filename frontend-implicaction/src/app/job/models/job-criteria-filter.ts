import {ContractEnumCode} from '../../shared/enums/contract.enum';
import {Criteria} from '../../shared/models/Criteria';

export interface JobCriteriaFilter extends Criteria {
  search?: string;
  contractType?: ContractEnumCode;
}
