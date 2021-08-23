import { Component, OnInit } from '@angular/core';

import { MatDialog } from '@angular/material/dialog';

import { LoginPopupComponent } from './login-popup/login-popup.component';

@Component({
  selector: 'app-login',
  template: ''
})
export class LoginComponent implements OnInit {

  constructor(
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.dialog.open(LoginPopupComponent, {
      width: '300px',
      // height: '250px',
      panelClass: 'no-padding-modal',
      disableClose: true
    });
  }

}
