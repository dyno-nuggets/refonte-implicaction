import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {WorkExperience} from '../models/work-experience';

@Injectable({
  providedIn: 'root'
})
export class ExperiencesContexteService {

  private behaviorSubject = new BehaviorSubject<WorkExperience[]>([]);

  constructor() {
  }

  setExperiences(experiences: WorkExperience[]): void {
    this.behaviorSubject.next(experiences);
  }

  observeExperiences(): Observable<WorkExperience[]> {
    return this.behaviorSubject.asObservable();
  }

  addExperience(experience: WorkExperience): void {
    const experiences = this.behaviorSubject.getValue();
    experiences.push(experience);
  }

  updateExperience(experience: WorkExperience): void {
    const experiences = this.behaviorSubject.getValue();
    const index = experiences.findIndex(xp => xp.id === experience.id);
    experiences[index] = experience;
  }
}
