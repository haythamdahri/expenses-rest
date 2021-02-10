import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less'],
})
export class LoginComponent implements OnInit, OnDestroy{

  authRequest: { username: string; password: string } = {username: '', password: ''};
  private authSubscription: Subscription;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  ngOnDestroy(): void {
    if( this.authSubscription != null ) {
      this.authSubscription.unsubscribe();
    }
  }

  login() {
    if (this.authRequest.username === '' || this.authRequest.password === '') {
      alert('Username and password are required!');
      return;
    }
    this.authSubscription = this.authService
      .signIn(this.authRequest).subscribe(
        (userToken) => {
          this.router.navigate(['']);
        },
        (err) => {
          alert("Login failed, please check username and password");
        }
      )
  }
}
