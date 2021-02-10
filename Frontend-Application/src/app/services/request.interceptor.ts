import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RequestInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // Retrieve connected user info
    const userToken = this.authService.getAuthenticatedUser();
    if( this.authService.isAuthenticated() && userToken != null && userToken.bearerToken != null )
    request = request.clone({
      setHeaders: {
        Authorization: userToken.bearerToken
      }
    });
    // Forward request handling
    return next.handle(request);
  }
}
