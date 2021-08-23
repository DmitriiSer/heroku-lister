import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  title = 'Lister';
  user = '';
  pictureUrl = '';

  constructor(
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
    this.authService.getUserDetails().subscribe(userDetails => {
      console.log(`User details:`, userDetails);
      this.user = userDetails.name;
      this.pictureUrl = userDetails?.picture?.data?.url;
    });
  }

  createList() { }

  logout() {
    this.authService.facebookLogout();
  }

}
