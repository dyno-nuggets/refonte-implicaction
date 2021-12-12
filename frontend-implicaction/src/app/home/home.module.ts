import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {IndexComponent} from './components/index/index.component';
import {FeatherModule} from 'angular-feather';
import {EventCardComponent} from './components/event-card/event-card.component';
import {PostCardComponent} from './components/post-card/post-card.component';
import {PostListComponent} from './components/post-list/post-list.component';
import {HomeRoutingModule} from './home-routing.module';
import {AvatarModule} from 'primeng/avatar';
import {JobCardComponent} from './components/job-card/job-card.component';
import {JobListComponent} from './components/job-list/job-list.component';
import {SharedModule} from '../shared/shared.module';


@NgModule({
  declarations: [
    IndexComponent,
    EventCardComponent,
    PostCardComponent,
    PostListComponent,
    JobCardComponent,
    JobListComponent
  ],
  imports: [
    CommonModule,
    FeatherModule,
    HomeRoutingModule,
    AvatarModule,
    SharedModule
  ],
  exports: [
    PostListComponent
  ]
})
export class HomeModule {
}
