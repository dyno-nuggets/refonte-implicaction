import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CommunityComponent} from './community.component';
import {SharedModule} from '../shared/shared.module';
import {CommunityRoutingModule} from './community-routing.module';


@NgModule({
  declarations: [
    CommunityComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    CommunityRoutingModule
  ]
})
export class CommunityModule {
}
