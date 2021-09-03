import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProfileRoutingModule} from "./profile-routing.module";
import {ProfileComponent} from "./profile.component";
import { PrimaryPersonalCardComponent } from './components/primary-personal-card/primary-personal-card.component';
import {CardModule} from "primeng/card";



@NgModule({
  declarations: [
    ProfileComponent,
    PrimaryPersonalCardComponent
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    CardModule
  ]
})
export class ProfileModule { }
