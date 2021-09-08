import {Injectable} from '@angular/core';
import {ApiHttpService} from '../../core/services/api-http.service';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Observable, of} from 'rxjs';
import {User} from '../models/user';
import {Pageable} from '../models/pageable';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private apiHttpService: ApiHttpService,
    private apiEndpointsService: ApiEndpointsService
  ) {

  }

  getAll(pageable: Pageable): Observable<Pageable<User>> {
    return (this.apiHttpService
      .get(this.apiEndpointsService.getAllUserEndpoint(pageable)) as Observable<Pageable<User>>);
  }

  getUser(userId: string): Observable<User> {
    return this.http.get(this.apiEndpointsService.getUserEndpoint(userId));
  }

  // getUser(userId: string): Observable<User> {
  //   return of({
  //     id: '1',
  //     username: 'Mel',
  //     email: 'melanie.da-costa@gmail.com',
  //     url: 'mel.com',
  //     nicename: 'Mélanie DA COSTA',
  //     birthday: '26/07/1996',
  //     phoneNumber: '0606060606',
  //     hobbies: 'Badminton, Série TV, Jeux vidéo, Musique',
  //     presentation: 'Etudiante en informatique',
  //     purpose: 'Trouver du travail',
  //     expectation: 'm\'enrichir',
  //     contribution: 'A lui de voir !',
  //     armyCorps: 'terre',
  //     rank: 'officier',
  //     workExperiences: [
  //       {
  //         description: 'heyyy Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur nec volutpat eros.',
  //         label: 'Ingénieur informaticien C#',
  //         companyLabel: 'Air France',
  //         startedAt: 'Décembre 2020',
  //         finishedAt: 'Juillet 2021',
  //       },
  //       {
  //         description: 'heyyy Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur nec volutpat eros.',
  //         label: 'Ingénieur informaticien C#',
  //         companyLabel: 'Air France',
  //         startedAt: 'Décembre 2020',
  //         finishedAt: 'Juillet 2021',
  //       },
  //       {
  //         description: 'heyyyy Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur nec volutpat eros.',
  //         label: 'Ingénieur informaticien C#',
  //         companyLabel: 'Air France',
  //         startedAt: 'Décembre 2020',
  //         finishedAt: 'Juillet 2021',
  //       },
  //     ]
  //   });
  // }

}
