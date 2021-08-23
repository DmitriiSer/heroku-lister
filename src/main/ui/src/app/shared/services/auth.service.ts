import { HttpClient } from '@angular/common/http';
import { Injectable, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, EMPTY, from, Observable, of, OperatorFunction } from 'rxjs';

import { UserDetails } from '../../model/user-details';

import { environment } from '../../../environments/environment';

interface Window {
  [key: string]: any; // Add index signature
}

declare const window: Window;

@Injectable({ providedIn: 'root' })
export class AuthService {

  private USER_DETAILS_KEY = 'userDetails';
  private baseUrl = '/api';

  private facebookAuth: BehaviorSubject<fb.AuthResponse> = new BehaviorSubject<fb.AuthResponse>(null as any);

  private userDetails: UserDetails | null = null;

  constructor(
    private router: Router,
    private http: HttpClient,
    private zone: NgZone
  ) { }

  initFacebook(resolve: (value: void | PromiseLike<void>) => void): void {
    window.fbAsyncInit = () => {
      FB.init({
        appId: environment.facebookAppId,
        xfbml: true,
        version: 'v11.0'
      });

      // FB.AppEvents.logPageView();
      // auto authenticate with the api if already logged in with facebook
      FB.getLoginStatus(statusResponse => {
        console.log(`initFacebook()`);
        this.getAuthResponse(statusResponse).toPromise().then(() => resolve());
      });
    };

    (function (d, s, id) {
      var js: any, fjs: any = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) { return; }
      js = d.createElement(s); js.id = id;
      js.src = "https://connect.facebook.net/en_US/sdk.js";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
  }

  facebookLogin(): Observable<fb.AuthResponse> {
    console.log(`facebookLogin()`);

    // login with facebook and return observable with fb access token on success    
    return from(
      this.checkStatus().toPromise().then(authResponse => {
        if (authResponse == null) {
          FB.login();
        }
        return authResponse;
      })).pipe(
        // concatMap(statusResponse => this.getToken(statusResponse)),
        this.runInZone(this.zone)
      );
  }

  facebookLogout(): void {
    console.log(`facebookLogout()`);
    this.zone.run(() => {
      // revoke app permissions to logout completely because FB.logout() doesn't remove FB cookie    
      FB.api('/me/permissions', 'delete', null as any, () => FB.logout());
      this.facebookAuth.next(null as any);
      this.userDetails = null;
      // TODO:
      // sessionStorage.removeItem(this.USER_DETAILS_KEY);
      this.router.navigateByUrl('/', { skipLocationChange: true })
        .then(() => {
          this.router.navigate(['/login'])
          setTimeout(() => window.location.reload(), 2000);
        });
    });

  }

  isLoggedIn(): BehaviorSubject<fb.AuthResponse> {
    return this.facebookAuth;
  }

  getUserDetails(): Observable<UserDetails> {
    return from(new Promise<any>(resolve => {
      const sessionUserDetails = sessionStorage.getItem(this.USER_DETAILS_KEY) as string;
      if (sessionUserDetails != null) {
        this.userDetails = null;
        try {
          this.userDetails = JSON.parse(sessionUserDetails);
        } catch (e) {
          resolve(null);
          return;
        }
        console.log(`User details found in the cache.`);
        resolve(this.userDetails);
      }
      if (this.userDetails == null) {
        FB.api('/me', (userDetails: any) => {
          this.userDetails = userDetails;
          FB.api(`/${userDetails.id}/picture?redirect=false&height=16`, 'get', {}, pictureDetails => {
            console.log(`pictureDetails:`, pictureDetails);
            // Insert your code here
            if (this.userDetails != null) {
              this.userDetails.picture = pictureDetails as any;
              sessionStorage.setItem(this.USER_DETAILS_KEY, JSON.stringify(this.userDetails));
              console.log(`Good to see you, ${userDetails.name}.`);
              resolve(this.userDetails);
            }
          });
        });
      }
    }));
  }

  private checkStatus(): Observable<fb.AuthResponse> {
    return from(new Promise<fb.AuthResponse>(resolve => FB.getLoginStatus(statusResponse => {
      this.getAuthResponse(statusResponse).toPromise().then(authResponse => resolve(authResponse))
    })));
  }

  private getAuthResponse(statusResponse: fb.StatusResponse): Observable<fb.AuthResponse> {
    console.log(`status[${statusResponse != null ? statusResponse.status : 'null'}]:`, statusResponse != null ? statusResponse.authResponse : null);
    if (statusResponse == null) {
      return of(null as any);
    } else {
      if (statusResponse.authResponse) {
        console.log('Welcome! Fetching your information.... ');
        this.zone.run(() => {
          this.facebookAuth.next(statusResponse.authResponse);
        });
        return of(statusResponse.authResponse);
      } else {
        console.log('User cancelled login or did not fully authorize.');
        this.facebookAuth.next(null as any);
        this.userDetails = null;
        sessionStorage.removeItem(this.USER_DETAILS_KEY);
        return EMPTY;
      }

    }
  }

  private runInZone<T>(zone: NgZone): OperatorFunction<T, T> {
    return (source) => {
      return new Observable(observer => {
        const onNext = (value: T) => zone.run(() => observer.next(value));
        const onError = (e: any) => zone.run(() => observer.error(e));
        const onComplete = () => zone.run(() => observer.complete());
        return source.subscribe(onNext, onError, onComplete);
      });
    };
  }

}
