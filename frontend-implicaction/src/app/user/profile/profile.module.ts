import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProfileRoutingModule} from "./profile-routing.module";
import {ProfileComponent} from "./profile.component";
import { PersonalCardComponent } from './components/personal-card/personal-card.component';
import {CardModule} from "primeng/card";
import {SharedModule} from "../../shared/shared.module";
import {IconsModule} from "../../icons/icons.module";
import { WorkExperienceComponent } from './components/work-experience/work-experience.component';



@NgModule({
  declarations: [
    ProfileComponent,
    PersonalCardComponent,
    WorkExperienceComponent
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
