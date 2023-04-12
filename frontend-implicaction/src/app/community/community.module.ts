import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CommunityRoutingModule} from "./community-routing.module";
import {UserCardComponent} from "./components/user-list/user-card/user-card.component";
import {RelationListComponent} from "./pages/relation-list/relation-list.component";
import {PaginatorModule} from "primeng/paginator";
import {RouterLinkActive, RouterLinkWithHref} from "@angular/router";
import {SharedModule} from "../shared/shared.module";
import {UserInfoComponent} from "./components/profile/user-info/user-info.component";
import {ExperienceListComponent} from "./components/profile/experience-list/experience-list.component";
import {SidebarProfileComponent} from "./components/profile/sidebar-profile/sidebar-profile.component";
import {TrainingListComponent} from "./components/profile/training-list/training-list.component";
import {ExperienceFormComponent} from "./components/profile/experience-form/experience-form.component";
import {TrainingFormComponent} from "./components/profile/training-form/training-form.component";
import {UserProfilePageComponent} from "./pages/user-profile-page/user-profile-page.component";
import {ProfileOverviewTabComponent} from "./components/profile/tabs/profile-overview/profile-overview-tab/profile-overview-tab.component";
import {EditProfileTabComponent} from "./components/profile/tabs/edit-profile-tab/edit-profile-tab.component";
import {EditSettingsTabComponent} from "./components/profile/tabs/edit-settings-tab/edit-settings-tab.component";
import {ChangePasswordTabComponent} from "./components/profile/tabs/change-password-tab/change-password-tab.component";
import {ProfileBlockComponent} from "./components/profile/tabs/profile-overview/profile-block/profile-block.component";
import {ProfileAboutSectionComponent} from "./components/profile/tabs/profile-overview/profile-about-section/profile-about-section.component";
import {ProfileDetailsSectionComponent} from "./components/profile/tabs/profile-overview/profile-details-section/profile-details-section.component";
import {ProfilePresentationSectionComponent} from "./components/profile/tabs/profile-overview/profile-presentation-section/profile-presentation-section.component";
import {ProfileWorkExperiencesSectionComponent} from "./components/profile/tabs/profile-overview/profile-work-experiences-section/profile-work-experiences-section.component";
import {ReactiveFormsModule} from "@angular/forms";
import {SkeletonModule} from "primeng/skeleton";
import {CalendarModule} from "primeng/calendar";
import {FeatherModule} from "angular-feather";
import { ProfileAvatarFileUploaderComponent } from './components/profile/profile-avatar-file-uploader/profile-avatar-file-uploader.component';


@NgModule({
  declarations: [
    UserCardComponent,
    RelationListComponent,
    UserInfoComponent,
    ExperienceListComponent,
    SidebarProfileComponent,
    TrainingListComponent,
    ExperienceFormComponent,
    TrainingFormComponent,
    UserProfilePageComponent,
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
  ]
})
export class CommunityModule {
}
