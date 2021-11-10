import {Injectable} from '@angular/core';
import {FilterContextService} from '../../shared/services/filter-context.service';
import {CompanyCriteriaFilter} from '../../job/models/company-criteria-filter';

@Injectable({
  providedIn: 'root'
})
export class CompanyFilterContextService extends FilterContextService<CompanyCriteriaFilter> {

}
