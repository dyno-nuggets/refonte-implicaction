import {ApplyStatusEnum} from '../enums/apply-status-enum';

export interface JobApply {
  id?: string;
  jobId: string;
  jobTitle: string;
  location: string;
  companyName: string;
  companyImageUrl: string;
  status: ApplyStatusEnum;
  contractType: string;
}
