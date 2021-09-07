import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserRoutingModule} from "./user-routing.module";
import {ProfileModule} from "./profile/profile.module";


@NgModule({
  imports: [
    CommonModule,
    UserRoutingModule,
    ProfileModule,
  ]
})
export class UserModule { }
