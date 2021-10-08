import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {WorkExperience} from '../models/work-experience';
import {User} from '../models/user';
import {Training} from '../models/training';

@Injectable({
  providedIn: 'root'
})
export class UserContextService {

  private behaviorSubject = new BehaviorSubject<User>({});

  setUser(user: User): void {
    this.behaviorSubject.next(user);
  }

  observeUser(): Observable<User> {
    return this.behaviorSubject.asObservable();
  }

  addExperience(experience: WorkExperience): void {
    const user = this.behaviorSubject.getValue();
    if (user.experiences) {
      user.experiences.push(experience);
    } else {
      user.experiences = [experience];
    }
  }

  updateExperience(experience: WorkExperience): void {
    const user = this.behaviorSubject.getValue();
    const index = user?.experiences.findIndex(xp => xp.id === experience.id);
    if (index > -1) {
      user.experiences[index] = experience;
      this.behaviorSubject.next(user);
    }
  }

  addTraining(training: Training): void {
    const user = this.behaviorSubject.getValue();
    if (user.experiences) {
      user.trainings.push(training);
    } else {
      user.trainings = [training];
    }
  }

  updateTraining(training: Training): void {
    const user = this.behaviorSubject.getValue();
    const index = user?.trainings.findIndex(xp => xp.id === training.id);
    if (index > -1) {
      user.trainings[index] = training;
      this.behaviorSubject.next(user);
    }
  }

  removeTraining(training: Training): void {
    const user = this.behaviorSubject.getValue();
    user.trainings = user.trainings.filter(t => t.id !== training.id);
    this.behaviorSubject.next(user);
  }

  removeExperience(experience: WorkExperience): void {
    const user = this.behaviorSubject.getValue();
    user.experiences = user.experiences.filter(e => e.id !== experience.id);
    this.behaviorSubject.next(user);
  }
}
