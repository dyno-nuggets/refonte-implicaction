import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {WorkExperience} from '../models/work-experience';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserContexteService {

  private behaviorSubject = new BehaviorSubject<User>({});

  constructor() {
  }

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
}
