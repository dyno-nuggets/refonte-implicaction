import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Observable} from 'rxjs';
import {Pageable} from '../../shared/models/pageable';

@Injectable({
  providedIn: 'root'
})
export class ContractService {

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }

  getAll(pageable: Pageable): Observable<any> {
    return this.http.get(this.apiEndpointsService.getAllContractsEndpoint(pageable));
  }
}
