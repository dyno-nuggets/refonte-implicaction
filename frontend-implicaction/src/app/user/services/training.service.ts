import {Injectable} from '@angular/core';
import {Training} from '../../shared/models/training';
import {HttpClient} from '@angular/common/http';
import {ApiEndpointsService} from '../../core/services/api-endpoints.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TrainingService {

  constructor(
    private http: HttpClient,
    private apiEndpointsService: ApiEndpointsService
  ) {
  }

  createTraining(training: Training): Observable<Training> {
    return this.http.post<Training>(this.apiEndpointsService.createTrainingEndpoint(), training);
  }

  updateTraining(training: Training): Observable<Training> {
    return this.http.put<Training>(this.apiEndpointsService.updateTrainingEndpoint(), training);
  }

  deleteTraining(trainingId: string): Observable<any> {
    return this.http.delete(this.apiEndpointsService.deleteTrainingEndpoint(trainingId));
  }
}
