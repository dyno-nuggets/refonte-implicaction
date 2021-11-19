import {Injectable} from '@angular/core';
import {BaseContextServiceService} from './base-context-service.service';
import {Company} from '../models/company';

@Injectable({
  providedIn: 'root'
})
export class CompanyContextServiceService extends BaseContextServiceService<Company> {

  constructor() {
    super();
  }
}
