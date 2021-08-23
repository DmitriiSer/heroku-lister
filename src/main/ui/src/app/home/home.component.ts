import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  authUser = '';

  constructor(
    public oktaAuth: OktaAuthService,
    private http: HttpClient,
    private authService: AuthService
  ) {
  }

  ngOnInit() {
    this.authService.isLoggedIn().subscribe(auth => {
      if (auth) {
        this.authService.getUserDetails().subscribe(userDetails => {
          this.authUser = userDetails.name;
          console.log(`User details:`, userDetails);
        })
      }
    });
  }

  logout() {
    this.authService.facebookLogout();
  }

}