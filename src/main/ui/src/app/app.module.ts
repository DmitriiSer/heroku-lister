import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HttpClientModule } from '@angular/common/http';

import { HomeModule } from './modules/home/home.module';
import { LoginModule } from './modules/login/login.module';
import { ListsModule } from './modules/lists/lists.module';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';

import { AuthService } from './shared/services/auth.service';

import { appInitializer } from './app-initializer';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    //
    HttpClientModule,
    //
    LoginModule,
    HomeModule,
    ListsModule,
    //
    AppRoutingModule
  ],
  bootstrap: [AppComponent],
  providers: [
    { provide: APP_INITIALIZER, useFactory: appInitializer, deps: [AuthService], multi: true }
  ]
})
export class AppModule { }
