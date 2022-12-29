import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiEndpointsService} from "../../core/services/api-endpoints.service";
import {Observable} from "rxjs";
import {Category} from "../model/category";


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(
    private http: HttpClient,
    private apiEndpointService: ApiEndpointsService
  ) {
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.apiEndpointService.getAllCategories());
  }
}
