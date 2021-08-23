import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AuthRoutingModule } from './auth-routing.module';

import { LoginModule } from './modules/login/login.module';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';

import { appInitializer } from './app-initializer';
import { AuthService } from './shared/services/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    //
    LoginModule,
    //
    AppRoutingModule,
    AuthRoutingModule
  ],
  bootstrap: [AppComponent],
  providers: [
    { provide: APP_INITIALIZER, useFactory: appInitializer, deps: [AuthService], multi: true }
  ]
})
export class AppModule { }
