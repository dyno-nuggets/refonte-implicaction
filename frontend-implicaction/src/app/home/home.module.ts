import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomePageComponent} from './pages/home-page/home-page.component';
import {FeatherModule} from 'angular-feather';
import {LatestEventCardComponent} from './components/cards/latest-event-card/latest-event-card.component';
import {LatestPostCardComponent} from './components/cards/latest-post-card/latest-post-card.component';
import {LatestPostsListComponent} from './components/lists/latest-posts-list/latest-posts-list.component';
import {HomeRoutingModule} from './home-routing.module';
import {AvatarModule} from 'primeng/avatar';
import {LatestJobCardComponent} from './components/cards/latest-job-card/latest-job-card.component';
import {LatestJobsListComponent} from './components/lists/latest-jobs-list/latest-jobs-list.component';
import {SharedModule} from '../shared/shared.module';
import {HomeHeadingColumnComponent} from './components/home-heading-column/home-heading-column.component';
import {LatestPostCardSkeletonComponent} from './components/cards/latest-post-card-skeleton/latest-post-card-skeleton.component';
import {SkeletonModule} from "primeng/skeleton";
import {LatestEventCardSkeletonComponent} from './components/cards/latest-event-card-skeleton/latest-event-card-skeleton.component';
import {LatestEventsListComponent} from './components/lists/latest-events-list/latest-events-list.component';
import {HighlightPointComponent} from './components/highlight-point/highlight-point.component';
import {LatestJobCartSkeletonComponent} from './components/cards/latest-job-cart-skeleton/latest-job-cart-skeleton.component';


@NgModule({
  declarations: [
    HomePageComponent,
    LatestEventCardComponent,
    LatestPostCardComponent,
    LatestPostsListComponent,
    LatestJobCardComponent,
    LatestJobsListComponent,
    HomeHeadingColumnComponent,
    LatestPostCardSkeletonComponent,
    LatestEventCardSkeletonComponent,
    LatestEventsListComponent,
    HighlightPointComponent,
    LatestJobCartSkeletonComponent,
  ],
  imports: [
    CommonModule,
    FeatherModule,
    HomeRoutingModule,
    AvatarModule,
    SharedModule,
    SkeletonModule
  ]
})
export class HomeModule {
}
