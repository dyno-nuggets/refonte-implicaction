import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AuthModule} from './auth/auth.module';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule, HttpClientXsrfModule} from '@angular/common/http';
import {NgxWebstorageModule} from 'ngx-webstorage';
import {SharedModule} from './shared/shared.module';
import {CoreModule} from './core/core.module';
import {ToastModule} from 'primeng/toast';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MessageService} from 'primeng/api';
import {SidebarModule} from 'primeng/sidebar';
import {BoardModule} from './board/board.module';
import {registerLocaleData} from '@angular/common';
import localeFr from '@angular/common/locales/fr';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NgxWebstorageModule.forRoot(),
    AuthModule,
    SharedModule,
    CoreModule,
    BrowserAnimationsModule,
    ToastModule,
    SidebarModule,
    BoardModule,
    HttpClientXsrfModule
  ],
  providers: [MessageService, {provide: LOCALE_ID, useValue: 'fr'}],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor() {
    registerLocaleData(localeFr, 'fr'); // passage du format de date en français
  }
}
