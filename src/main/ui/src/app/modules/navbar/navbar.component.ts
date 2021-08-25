import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
    private router: Router,
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
    this.authService.getUserDetails().subscribe(userDetails => {
      console.log(`User details:`, userDetails);
      this.user = userDetails.name;
      this.pictureUrl = userDetails?.picture?.data?.url;
    });
  }

  createList() {
    this.router.navigate(['/lists/new']);
  }

  logout() {
    this.authService.facebookLogout();
  }

}
