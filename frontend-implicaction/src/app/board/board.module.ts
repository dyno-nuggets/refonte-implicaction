import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BoardComponent} from './board.component';
import {BrowserModule} from '@angular/platform-browser';


@NgModule({
  declarations: [
    BoardComponent
  ],
  imports: [
    CommonModule,
    BrowserModule
  ]
})
export class BoardModule {
}
