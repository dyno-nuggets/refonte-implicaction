import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {JobPosting} from '../models/job-posting';

@Injectable({
  providedIn: 'root'
})
export class JobContextService {

  private behaviorSubject = new BehaviorSubject<JobPosting>({});

  constructor() {
  }

  setJob(job: JobPosting): void {
    this.behaviorSubject.next(job);
  }

  observeJob(): Observable<JobPosting> {
    return this.behaviorSubject.asObservable();
  }

  updateJob(jobUpdate: JobPosting) {
    const job = this.behaviorSubject.getValue();
    job.company = jobUpdate.company;
    job.contractType = jobUpdate.contractType;
    this.behaviorSubject.next(jobUpdate);
  }
}
