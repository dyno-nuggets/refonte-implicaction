import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Pageable} from '../../shared/models/pageable';
import {Observable} from 'rxjs';
import {Company} from '../../shared/models/company';
import {Criteria} from '../../shared/models/Criteria';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }

  getAllByCriteria(pageable: Pageable, criteria: Criteria): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllCompanyByCriteriaEndpoint(pageable, criteria));
  }

  getAll(pageable: Pageable): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllCompanyEndpoint(pageable));
  }

  createCompany(company: Company): Observable<Company> {
    return this.http.post<Company>(this.apiEndpointsService.createCompanyEndpoint(), company);
  }

  updateCompany(company: Company): Observable<Company> {
    return this.http.put<Company>(this.apiEndpointsService.updateCompanyEndpoint(), company);
  }
}
