import {ApplyStatusCode} from '../enums/apply-status-enum';

export interface JobApplicationRequest {
  jobId?: string;
  status: ApplyStatusCode;
}
