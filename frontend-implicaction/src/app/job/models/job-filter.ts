import {BaseFilter} from '../../shared/models/base-filter';

export interface JobFilter extends BaseFilter {
  contractType?: string;
  city?: string;
}
