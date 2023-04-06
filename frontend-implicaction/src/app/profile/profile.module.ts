import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileRoutingModule} from './profile-routing.module';
import {UserInfoComponent} from './components/user-info/user-info.component';
import {ExperienceListComponent} from './components/experience-list/experience-list.component';
import {UserProfileOldComponent} from './pages/user-profile-old/user-profile-old.component';
import {SidebarProfileComponent} from './components/sidebar-profile/sidebar-profile.component';
import {FeatherModule} from 'angular-feather';
import {PaginatorModule} from 'primeng/paginator';
import {TrainingListComponent} from './components/training-list/training-list.component';
import {CalendarModule} from 'primeng/calendar';
import {SharedModule} from '../shared/shared.module';
import {ExperienceFormComponent} from './components/experience-form/experience-form.component';
import {ReactiveFormsModule} from '@angular/forms';
import {TrainingFormComponent} from './components/training-form/training-form.component';
import {SkeletonModule} from 'primeng/skeleton';
import {ProfilPicWithHoverComponent} from './components/profil-pic-with-hover/profil-pic-with-hover.component';
import {UserProfilePageComponent} from './pages/user-profile-page/user-profile-page.component';
import {ProfileOverviewTabComponent} from './components/tabs/profile-overview/profile-overview-tab/profile-overview-tab.component';
import {EditProfileTabComponent} from './components/tabs/edit-profile-tab/edit-profile-tab.component';
import {EditSettingsTabComponent} from './components/tabs/edit-settings-tab/edit-settings-tab.component';
import {ChangePasswordTabComponent} from './components/tabs/change-password-tab/change-password-tab.component';
import {ProfileBlockComponent} from './components/tabs/profile-overview/profile-block/profile-block.component';
import {ProfileAboutSectionComponent} from './components/tabs/profile-overview/profile-about-section/profile-about-section.component';
import {ProfileDetailsSectionComponent} from './components/tabs/profile-overview/profile-details-section/profile-details-section.component';
import {ProfilePresentationSectionComponent} from './components/tabs/profile-overview/profile-presentation-section/profile-presentation-section.component';
import {ProfileWorkExperiencesSectionComponent} from './components/tabs/profile-overview/profile-work-experiences-section/profile-work-experiences-section.component';


@NgModule({
  declarations: [
    UserInfoComponent,
    ExperienceListComponent,
    UserProfileOldComponent,
    SidebarProfileComponent,
    TrainingListComponent,
    ExperienceFormComponent,
    TrainingFormComponent,
    ProfilPicWithHoverComponent,
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
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    FeatherModule,
    PaginatorModule,
    CalendarModule,
    SharedModule,
    ReactiveFormsModule,
    SkeletonModule,
  ],
})
export class ProfileModule {
}
