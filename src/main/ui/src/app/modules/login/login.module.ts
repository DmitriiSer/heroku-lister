import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatDialogModule } from '@angular/material/dialog';

import { LoginComponent } from './login.component';
import { LoginPopupComponent } from './login-popup/login-popup.component';

@NgModule({
  imports: [
    CommonModule,
    MatDialogModule
  ],
  declarations: [
    LoginComponent,
    LoginPopupComponent
  ],
  entryComponents: [LoginPopupComponent]
})
export class LoginModule { }
