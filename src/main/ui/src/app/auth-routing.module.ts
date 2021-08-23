import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OKTA_CONFIG, OktaAuthModule } from '@okta/okta-angular';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AuthInterceptor } from './shared/okta/auth.interceptor';

const oktaConfig = {
  issuer: 'https://dev-4682938.okta.com/oauth2/default',
  redirectUri: window.location.origin + '/callback',
  clientId: '',
  scopes: ['openid', 'profile']
};

const routes: Routes = [
  // { path: '', redirectTo: '/home-okta', pathMatch: 'full' },
  // { path: 'home-okta', component: HomeComponent },
  // { path: 'callback', component: OktaCallbackComponent }
];

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    OktaAuthModule,
    RouterModule.forRoot(routes)
  ],
  providers: [
    { provide: OKTA_CONFIG, useValue: oktaConfig },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
