import {ApplyStatusCode} from '../enums/apply-status-enum';

export interface JobApplication {
  id?: string;
  jobId: string;
  jobTitle: string;
  location: string;
  companyName: string;
  companyImageUri: string;
  statusCode: ApplyStatusCode;
  contractType: string;
  archive?: boolean;
}
