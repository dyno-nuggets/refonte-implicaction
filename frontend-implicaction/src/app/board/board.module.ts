import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BoardComponent} from './board.component';
import {BrowserModule} from '@angular/platform-browser';
import {TagModule} from 'primeng/tag';
import {ApplyCardComponent} from './components/apply-card/apply-card.component';
import {SharedModule} from '../shared/shared.module';
import {RouterModule} from '@angular/router';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MenuModule} from 'primeng/menu';
import {CardMenuComponent} from './components/card-menu/card-menu.component';


@NgModule({
  declarations: [
    BoardComponent,
    ApplyCardComponent,
    CardMenuComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    TagModule,
    SharedModule,
    RouterModule,
    DragDropModule,
    MenuModule,
  ]
})
export class BoardModule {
}
