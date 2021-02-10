import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { UserAuthRequest } from 'src/app/models/user-auth-request.model';
import { UserAuthResponse } from 'src/app/models/user-auth-response.model';
import { environment } from 'src/environments/environment';
import { map, retry, catchError } from 'rxjs/operators';
import * as jwtDecode from 'jwt-decode';
import { UserToken } from 'src/app/models/user-token.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  static readonly AUTH_ENDPOINT = `${environment.api}/api/v1/auth/`;

  constructor(private httpClient: HttpClient) {}

  signIn(userRequest: UserAuthRequest): Observable<UserToken> {
    return this.httpClient
      .post<UserAuthResponse>(AuthService.AUTH_ENDPOINT, userRequest)
      .pipe(
        retry(5),
        map((userData) => {
          let userToken = this.decodeToken(userData?.token);
          localStorage.setItem('userToken', JSON.stringify(userToken));
          // Emit auth event
          return userToken;
        }, catchError(this.handleError))
      );
  }

  // Check if user is authenticated
  isAuthenticated() {
    let userToken: UserToken = JSON.parse(localStorage.getItem('userToken'));
    // Checking expiration and email
    if (userToken != null && this.isValidToken(userToken.exp)) {
      return true;
    } else {
      this.logout();
      return false;
    }
  }

  // Logout from the system
  logout() {
    localStorage.clear();
  }

  // Check expiration date
  isValidToken(expiration: number) {
    const now = new Date().getTime();
    return expiration > now;
  }

  // Get current user
  getAuthenticatedUser() {
    const userToken: UserToken = JSON.parse(localStorage.getItem('userToken'));
    if (userToken != null) {
      return userToken;
    } else {
      return null;
    }
  }

  // Decode token
  decodeToken(token: string) {
    var decoded = jwtDecode(token);
    let userToken = new UserToken();
    userToken.bearerToken = 'Bearer ' + token;
    userToken.token = token;
    userToken.email = decoded.sub;
    userToken.roles = decoded.roles;
    userToken.exp = Number(decoded.exp * 1000);
    return userToken;
  }

  handleError(error: Error) {
    return throwError(error);
  }
}
