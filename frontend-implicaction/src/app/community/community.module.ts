import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CommunityRoutingModule} from "./community-routing.module";
import {ProfileCardBaseComponent} from "./components/profile-list/profile-card-base/profile-card-base.component";
import {ProfileListPageComponent} from "./pages/profile-list-page/profile-list-page.component";
import {PaginatorModule} from "primeng/paginator";
import {RouterLinkActive, RouterLinkWithHref} from "@angular/router";
import {SharedModule} from "../shared/shared.module";
import {UserInfoComponent} from "./components/profile-details/user-info/user-info.component";
import {ProfileOverviewTabComponent} from "./components/profile-details/tabs/profile-overview/profile-overview-tab/profile-overview-tab.component";
import {EditProfileTabComponent} from "./components/profile-details/tabs/edit-profile-tab/edit-profile-tab.component";
import {EditSettingsTabComponent} from "./components/profile-details/tabs/edit-settings-tab/edit-settings-tab.component";
import {ChangePasswordTabComponent} from "./components/profile-details/tabs/change-password-tab/change-password-tab.component";
import {ProfileBlockComponent} from "./components/profile-details/tabs/profile-overview/profile-block/profile-block.component";
import {ProfileAboutSectionComponent} from "./components/profile-details/tabs/profile-overview/profile-about-section/profile-about-section.component";
import {ProfileDetailsSectionComponent} from "./components/profile-details/tabs/profile-overview/profile-details-section/profile-details-section.component";
import {ProfilePresentationSectionComponent} from "./components/profile-details/tabs/profile-overview/profile-presentation-section/profile-presentation-section.component";
import {
  ProfileWorkExperiencesSectionComponent
} from "./components/profile-details/tabs/profile-overview/profile-work-experiences-section/profile-work-experiences-section.component";
import {ReactiveFormsModule} from "@angular/forms";
import {SkeletonModule} from "primeng/skeleton";
import {CalendarModule} from "primeng/calendar";
import {FeatherModule} from "angular-feather";
import {ProfileAvatarFileUploaderComponent} from './components/profile-details/profile-avatar-file-uploader/profile-avatar-file-uploader.component';
import {ProfileEditFormComponent} from "./components/profile-details/forms/profile-edit-form/profile-edit-form.component";
import {ProfileDetailsPageComponent} from "./pages/profile-details-page/profile-details-page.component";
import {ProfileCardSkeletonComponent} from './components/profile-list/profile-card-skeleton/profile-card-skeleton.component';

import {TooltipModule} from 'primeng/tooltip';
import {RelationButtonComponent} from './components/relation-button/relation-button.component';


@NgModule({
  declarations: [
    ProfileCardBaseComponent,
    ProfileListPageComponent,
    UserInfoComponent,
    ProfileDetailsPageComponent,
    ProfileEditFormComponent,
    ProfileOverviewTabComponent,
    EditProfileTabComponent,
    EditSettingsTabComponent,
    ChangePasswordTabComponent,
    ProfileBlockComponent,
    ProfileAboutSectionComponent,
    ProfileDetailsSectionComponent,
    ProfilePresentationSectionComponent,
    ProfileWorkExperiencesSectionComponent,
    ProfileAvatarFileUploaderComponent,
    ProfileCardSkeletonComponent,
    ProfileCardBaseComponent,
    RelationButtonComponent,
  ],
  imports: [
    CommonModule,
    CommunityRoutingModule,
    PaginatorModule,
    RouterLinkActive,
    RouterLinkWithHref,
    SharedModule,
    ReactiveFormsModule,
    SkeletonModule,
    CalendarModule,
    FeatherModule,
    TooltipModule
  ]
})
export class CommunityModule {
}
