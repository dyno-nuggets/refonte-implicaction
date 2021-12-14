import {ContractEnumCode} from '../../shared/enums/contract.enum';
import {Criteria} from '../../shared/models/Criteria';
import {BusinessSectorEnumCode} from '../../shared/enums/sector.enum';

export interface JobCriteriaFilter extends Criteria {
  search?: string;
  contractType?: ContractEnumCode;
  businessSector?: BusinessSectorEnumCode;
}
