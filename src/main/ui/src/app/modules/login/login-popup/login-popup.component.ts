import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';

import { AuthService } from 'src/app/shared/services/auth.service';

declare const window: any;

@Component({
  selector: 'app-login-popup',
  templateUrl: './login-popup.component.html',
  styleUrls: ['./login-popup.component.scss']
})
export class LoginPopupComponent implements OnInit {

  authUser = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private dialogRef: MatDialogRef<LoginPopupComponent>
  ) { }

  ngOnInit(): void {
    window.login = this.login.bind(this);
    this.authService.isLoggedIn().subscribe(authResponse => {
      if (authResponse) {
        this.authUser = authResponse.userID;
        const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
        console.log(`User already authenticated... Redirecting to '${returnUrl}'`);
        this.router.navigateByUrl(returnUrl);
      }
    });
  }

  login() {
    this.authService.facebookLogin().subscribe(authResponse => {
      // redirect to the home page if user is already logged in      
      let returnUrl = '/';
      if (authResponse != null) {
        returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
      }
      console.log(`Successful login. Redirecting to '${returnUrl}`);
      this.router.navigateByUrl(returnUrl);
      this.dialogRef.close();
    });
  }

}
