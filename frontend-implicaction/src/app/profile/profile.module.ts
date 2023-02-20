import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileRoutingModule} from './profile-routing.module';
import {UserInfoComponent} from './components/user-info/user-info.component';
import {ExperienceListComponent} from './components/experience-list/experience-list.component';
import {UserProfileComponent} from './pages/user-profile/user-profile.component';
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


@NgModule({
  declarations: [
    UserInfoComponent,
    ExperienceListComponent,
    UserProfileComponent,
    SidebarProfileComponent,
    TrainingListComponent,
    ExperienceFormComponent,
    TrainingFormComponent,
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
  ]
})
export class ProfileModule {
}
