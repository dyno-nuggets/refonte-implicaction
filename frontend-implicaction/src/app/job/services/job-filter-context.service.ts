import {Injectable} from '@angular/core';
import {FilterContextService} from '../../shared/services/filter-context.service';
import {JobCriteriaFilter} from '../models/job-criteria-filter';

@Injectable({
  providedIn: 'root'
})
export class JobFilterContextService extends FilterContextService<JobCriteriaFilter> {

}
