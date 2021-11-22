import {Injectable} from '@angular/core';
import {FilterContextService} from '../../shared/services/filter-context.service';
import {Criteria} from '../../shared/models/Criteria';

@Injectable({
  providedIn: 'root'
})
export class CompanyFilterContextService extends FilterContextService<Criteria> {

}
