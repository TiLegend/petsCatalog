import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenParams } from '../classes/TokenParams';
import { AuthService } from '../auth.service';
import { LocalStorageService } from '../LocalStorageService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  tokenParam: TokenParams;
  username: string;
  password: string;

  constructor(
    private router: Router,
    private authService: AuthService,
    private LocalStorageService: LocalStorageService
  ) { }

  ngOnInit() {
  }

  doLogin(): void {
    this.authService.loginByHttpClient(this.username, this.password)
      .subscribe(
      data => {
        this.LocalStorageService.setAuthorizationData(data);
        this.router.navigate(['/pets']);
      },
      error => {
        alert(error.error.error_description)
      }
      );
  }

}
