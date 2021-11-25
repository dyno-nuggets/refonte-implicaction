import {ApplyStatusCode} from '../enums/apply-status-enum';

export interface JobApplicationRequest {
  jobId?: string;
  archive?: boolean;
  status: ApplyStatusCode;
}
