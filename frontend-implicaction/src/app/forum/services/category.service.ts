import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiEndpointsService} from "../../core/services/api-endpoints.service";
import {Observable} from "rxjs";
import {Category} from "../model/category";
import {Pageable} from "../../shared/models/pageable";
import {Topic} from "../model/topic";


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

  getCategory(id: number): Observable<Category> {
    return this.http.get<Category>(this.apiEndpointService.getCategory(id));
  }

  getCategoryTopics(id: number, pageable: Pageable<any>): Observable<Pageable<Topic>> {
    return this.http.get<Pageable<Topic>>(this.apiEndpointService.getCategoryTopics(id, pageable));
  }
}
