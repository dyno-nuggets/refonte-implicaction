import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserRoutingModule} from './user-routing.module';
import {UserCardComponent} from './components/user-card/user-card.component';
import {UserInfoComponent} from './components/user-info/user-info.component';
import {ExperienceDetailComponent} from './components/experience-detail/experience-detail.component';
import {ExperienceListComponent} from './components/experience-list/experience-list.component';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {PersonalCardComponent} from './components/personal-card/personal-card.component';
import {FeatherModule} from 'angular-feather';
import {UserListComponent} from './components/user-list/user-list.component';
import {PaginatorModule} from 'primeng/paginator';
import {TrainingListComponent} from './components/training-list/training-list.component';
import {TrainingDetailComponent} from './components/training-detail/training-detail.component';
import {SharedModule} from '../shared/shared.module';


@NgModule({
  declarations: [
    UserCardComponent,
    UserInfoComponent,
    ExperienceDetailComponent,
    ExperienceListComponent,
    UserProfileComponent,
    PersonalCardComponent,
    UserListComponent,
    TrainingListComponent,
    TrainingDetailComponent,
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    FeatherModule,
    PaginatorModule,
    SharedModule,
  ]
})
export class UserModule {
}
