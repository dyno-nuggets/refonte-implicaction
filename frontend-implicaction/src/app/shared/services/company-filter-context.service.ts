import {Injectable} from '@angular/core';
import {FilterContextService} from './filter-context.service';
import {Criteria} from '../models/Criteria';

@Injectable({
  providedIn: 'root'
})
export class CompanyFilterContextService extends FilterContextService<Criteria> {

}
