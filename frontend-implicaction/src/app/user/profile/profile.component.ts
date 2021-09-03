import { Component, OnInit } from '@angular/core';
import {UserService} from "../../shared/services/user.service";
import {User} from "../../shared/models/user";
import {ToasterService} from "../../core/services/toaster.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User;

  constructor(private userService: UserService, private toasterService: ToasterService) { }

  ngOnInit(): void {
    // TODO: Recuperer l'utilisateur en bd
    this.userService
      .getUser(1)
      .subscribe(
        user => this.user = user,
        () => this.toasterService.error('oops', 'Une erreur est survenue !')
      );
  }

}
