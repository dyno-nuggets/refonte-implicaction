import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CommunityRoutingModule} from "./community-routing.module";
import {UserCardComponent} from "./components/user-card/user-card.component";
import {RelationListComponent} from "./pages/relation-list/relation-list.component";
import {PaginatorModule} from "primeng/paginator";
import {RouterLinkActive, RouterLinkWithHref} from "@angular/router";
import {SharedModule} from "../shared/shared.module";


@NgModule({
  declarations: [
    UserCardComponent,
    RelationListComponent,
  ],
  imports: [
    CommonModule,
    CommunityRoutingModule,
    PaginatorModule,
    RouterLinkActive,
    RouterLinkWithHref,
    SharedModule,
  ]
})
export class CommunityModule {
}
