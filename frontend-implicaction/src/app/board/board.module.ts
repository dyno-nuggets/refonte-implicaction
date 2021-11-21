import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BoardComponent} from './board.component';
import {BrowserModule} from '@angular/platform-browser';
import {TagModule} from 'primeng/tag';
import {ApplyCardComponent} from './components/apply-card/apply-card.component';
import {SharedModule} from '../shared/shared.module';
import {RouterModule} from '@angular/router';
import {DragDropModule} from '@angular/cdk/drag-drop';


@NgModule({
  declarations: [
    BoardComponent,
    ApplyCardComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    TagModule,
    SharedModule,
    RouterModule,
    DragDropModule
  ]
})
export class BoardModule {
}
