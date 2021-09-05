import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CommunityComponent} from './community.component';
import {SharedModule} from '../shared/shared.module';
import {CommunityRoutingModule} from './community-routing.module';
import {IconsModule} from '../icons/icons.module';
import {PaginatorModule} from 'primeng/paginator';


@NgModule({
  declarations: [
    CommunityComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    CommunityRoutingModule,
    IconsModule,
    PaginatorModule
  ]
})
export class CommunityModule {
}
