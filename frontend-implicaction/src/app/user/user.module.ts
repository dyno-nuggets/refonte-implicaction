import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserRoutingModule} from './user-routing.module';
import {UserCardComponent} from './components/user-card/user-card.component';
import {UserInfoComponent} from './components/user-info/user-info.component';
import {ExperienceListComponent} from './components/experience-list/experience-list.component';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {SidebarProfileComponent} from './components/sidebar-profile/sidebar-profile.component';
import {FeatherModule} from 'angular-feather';
import {UserListComponent} from './components/user-list/user-list.component';
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
    UserCardComponent,
    UserInfoComponent,
    ExperienceListComponent,
    UserProfileComponent,
    SidebarProfileComponent,
    UserListComponent,
    TrainingListComponent,
    ExperienceFormComponent,
    TrainingFormComponent,
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    FeatherModule,
    PaginatorModule,
    CalendarModule,
    SharedModule,
    ReactiveFormsModule,
    SkeletonModule,
  ]
})
export class UserModule {
}
