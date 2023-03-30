import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {RelationListComponent} from "./pages/relation-list/relation-list.component";

const routes: Routes = [
  {
    path: 'relations/received',
    component: RelationListComponent,
  },
  {
    path: 'relations/sent',
    component: RelationListComponent,
  },
  {
    path: 'relations',
    component: RelationListComponent,
  },
  {
    path: '',
    component: RelationListComponent
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
})
export class CommunityRoutingModule {
}