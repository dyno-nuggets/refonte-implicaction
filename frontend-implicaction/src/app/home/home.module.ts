import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndexComponent } from './components/index/index.component';
import {FeatherModule} from 'angular-feather';
import { EventCardComponent } from './components/event-card/event-card.component';
import { PostCardComponent } from './components/post-card/post-card.component';
import { PostListComponent } from './components/post-list/post-list.component';


@NgModule({
  declarations: [
    IndexComponent,
    EventCardComponent,
    PostCardComponent,
    PostListComponent
  ],
  imports: [
    CommonModule,
    FeatherModule,
  ]
})
export class HomeModule {
}
