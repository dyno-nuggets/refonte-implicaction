import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ToasterService} from 'src/app/core/services/toaster.service';
import {UserService} from 'src/app/user/services/user.service';
import {RoleEnum, RoleEnumCode} from "../../shared/enums/role.enum";
import {User} from "../../shared/models/user";
import {AuthService} from '../../shared/services/auth.service';

@Component({
  selector: 'app-gestion-roles',
  templateUrl: './gestion-roles.component.html',
  styleUrls: ['./gestion-roles.component.scss'],

})
export class GestionRolesComponent implements OnInit {
  
  public roles = RoleEnum.all();
  public keyWord: string
  public userName: any
  public usersFind: string [];
  public userSelected: User = {roles: []};
  public isadmin: boolean;

  constructor(
    private userService: UserService,
    private toasterService: ToasterService,
    private router: Router,
    private ref: ChangeDetectorRef,
    private authService: AuthService
  ) {

  }

  ngOnInit(): void {
    this.isadmin = this.authService.getCurrentUser().roles.includes(RoleEnumCode.ADMIN)
  }


  usernameChange(event: { query: string }) {
    this.userService.getAllUsernameMatching(event.query).subscribe(
      usernames => this.usersFind = usernames
    )
  }

  usernameSelected(username: string) {
    this.userService.getUserByUsername(username).subscribe(
      user => {
        this.userSelected = user;
        this.userName = user.firstname + " " + user.lastname;
      }
    );

  }

  updateUser() {
    this.userService
      .updateRoleOfUser(this.userSelected)
      .subscribe(
        user => this.userSelected = user,
        () => this.toasterService.error('Oops', 'Une erreur est survenue lors de la mise à jour des données'),
        () => this.toasterService.success('Ok', 'Le changement des données a bien été effectué')
      );
  }
}




