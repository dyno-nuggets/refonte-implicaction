import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProfileRoutingModule} from "./profile-routing.module";
import {ProfileComponent} from "./profile.component";
import { PersonalCardComponent } from './components/personal-card/personal-card.component';
import {CardModule} from "primeng/card";
import {SharedModule} from "../../shared/shared.module";
import {IconsModule} from "../../icons/icons.module";



@NgModule({
  declarations: [
    ProfileComponent,
    PersonalCardComponent
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    CardModule,
    SharedModule,
    IconsModule
  ]
})
export class ProfileModule { }
