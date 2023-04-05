import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FooterComponent} from "./components/footer/footer.component";
import {SocialsAndLegalsComponent} from './components/socials-and-legals/socials-and-legals.component';
import {RouterLinkWithHref} from "@angular/router";
import { SupportsComponent } from './components/supports/supports.component';
import { ContactComponent } from './components/contact/contact.component';


@NgModule({
  declarations: [FooterComponent, SocialsAndLegalsComponent, SupportsComponent, ContactComponent],
  imports: [
    CommonModule,
    RouterLinkWithHref
  ],
  exports: [
    FooterComponent
  ]
})
export class FooterModule {
}
