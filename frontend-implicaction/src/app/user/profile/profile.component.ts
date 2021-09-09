import {Component, OnInit} from '@angular/core';
import {UserService} from "../../shared/services/user.service";
import {User} from "../../shared/models/user";
import {ToasterService} from "../../core/services/toaster.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User = {};

  constructor(private userService: UserService, private toasterService: ToasterService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      const userId = paramMap.get('userId');
      this.userService
        .getUser(userId)
        .subscribe(
          user => this.user = user,
          () => this.toasterService.error('oops', 'Une erreur est survenue !')
        );
    })
  }

}
