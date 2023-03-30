import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomePageComponent} from './pages/home-page/home-page.component';
import {FeatherModule} from 'angular-feather';
import {EventCardComponent} from './components/cards/event-card/event-card.component';
import {PostCardComponent} from './components/cards/post-card/post-card.component';
import {PostListComponent} from './components/lists/post-list/post-list.component';
import {HomeRoutingModule} from './home-routing.module';
import {AvatarModule} from 'primeng/avatar';
import {JobCardComponent} from './components/cards/job-card/job-card.component';
import {JobListComponent} from './components/lists/job-list/job-list.component';
import {SharedModule} from '../shared/shared.module';
import {HomeHeadingColumnComponent} from './components/home-heading-column/home-heading-column.component';
import {PostCardSkeletonComponent} from './components/cards/post-card-skeleton/post-card-skeleton.component';
import {SkeletonModule} from "primeng/skeleton";
import { EventCardSkeletonComponent } from './components/cards/event-card-skeleton/event-card-skeleton.component';
import { EventListComponent } from './components/lists/event-list/event-list.component';
import { ValuePointComponent } from './components/value-point/value-point.component';
import { JobCartSkeletonComponent } from './components/cards/job-cart-skeleton/job-cart-skeleton.component';


@NgModule({
  declarations: [
    HomePageComponent,
    EventCardComponent,
    PostCardComponent,
    PostListComponent,
    JobCardComponent,
    JobListComponent,
    HomeHeadingColumnComponent,
    PostCardSkeletonComponent,
    EventCardSkeletonComponent,
    EventListComponent,
    ValuePointComponent,
    JobCartSkeletonComponent
  ],
  imports: [
    CommonModule,
    FeatherModule,
    HomeRoutingModule,
    AvatarModule,
    SharedModule,
    SkeletonModule
  ],
  exports: [
    PostListComponent
  ]
})
export class HomeModule {
}
