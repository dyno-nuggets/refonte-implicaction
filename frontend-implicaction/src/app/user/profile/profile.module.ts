import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProfileRoutingModule} from "./profile-routing.module";
import {ProfileComponent} from "./profile.component";
import { PersonalCardComponent } from './components/personal-card/personal-card.component';
import {CardModule} from "primeng/card";
import {SharedModule} from "../../shared/shared.module";
import {IconsModule} from "../../icons/icons.module";
import { UserInfoDisplayComponent } from './components/user-info-display/user-info-display.component';
import { WorkExperienceDetailComponent } from './components/work-experience-detail/work-experience-detail.component';
import {WorkExperienceListComponent} from "./components/work-experience-list/work-experience-list.component";



@NgModule({
  declarations: [
    ProfileComponent,
    PersonalCardComponent,
    WorkExperienceListComponent,
    UserInfoDisplayComponent,
    WorkExperienceDetailComponent
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
