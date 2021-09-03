import {Injectable} from '@angular/core';
import {ApiHttpService} from '../../core/services/api-http.service';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Observable, of} from 'rxjs';
import {User} from '../models/user';
import {Pageable} from '../models/pageable';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private apiHttpService: ApiHttpService,
    private apiEndpointsService: ApiEndpointsService
  ) {

  }

  getAll(pageable: Pageable): Observable<Pageable<User>> {
    return (this.apiHttpService
      .get(this.apiEndpointsService.getAllUserEndpoint(pageable)) as Observable<Pageable<User>>);
  }

  getUser(userId: number): Observable<User> {
    return of({
      id: '1',
      username: 'Mel',
      email: 'mel@gmail.com',
      url: 'mel.com',
      dispayName: 'Memel',
      birthday: '26/07/1996',
      phoneNumber: '0606060606',
    })
  }
}
