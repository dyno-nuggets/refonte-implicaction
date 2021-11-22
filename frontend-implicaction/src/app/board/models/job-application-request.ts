import {ApplyStatusCode} from '../enums/apply-status-enum';

export interface JobApplicationRequest {
  id?: string;
  jobId: string;
  status: ApplyStatusCode;
}
