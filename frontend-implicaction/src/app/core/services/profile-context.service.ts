import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Profile} from "../../community/models/profile/profile";

@Injectable({
  providedIn: 'root'
})
export class ProfileContextService {

  profileSubject: BehaviorSubject<Profile> = new BehaviorSubject<Profile>({});

  observeProfile(): Observable<Profile> {
    return this.profileSubject.asObservable();
  }

  set profile(profile: Profile) {
    this.profileSubject.next(profile);
  }
}
