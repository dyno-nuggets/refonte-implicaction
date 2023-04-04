import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "../../services/profile.service";
import {Profile} from "../../models/profile";
import {Univers} from "../../../shared/enums/univers";
import {Observable} from "rxjs";

@Component({
  selector: 'app-user-profile-page',
  templateUrl: './user-profile-page.component.html',
  styleUrls: ['./user-profile-page.component.scss']
})
export class UserProfilePageComponent implements OnInit {
  profile$: Observable<Profile>;
  univers = Univers;

  constructor(
    private route: ActivatedRoute,
    private profileService: ProfileService
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      const username = paramMap.get('username');
      this.profile$ = this.profileService.getProfileByUsername(username);
    });
  }

}
