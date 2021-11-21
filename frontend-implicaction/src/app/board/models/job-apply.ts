import {ApplyStatusCode} from '../enums/apply-status-enum';

export interface JobApply {
  id?: string;
  jobId: string;
  jobTitle: string;
  location: string;
  companyName: string;
  companyImageUrl: string;
  statusCode: ApplyStatusCode;
  contractType: string;
}
