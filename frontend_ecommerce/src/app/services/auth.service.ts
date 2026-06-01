import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest } from '../models/login-request';
import { LoginResponse } from '../models/login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http = inject(HttpClient);

  private baseUrl =
    'http://localhost:8080/api/customers';

  login(
    request: LoginRequest
  ): Observable<LoginResponse> {

    return this.http.post<LoginResponse>(
      `${this.baseUrl}/login`,
      request
    );
  }

  logout(): void {

    localStorage.removeItem(
      'loggedInUser'
    );
  }

  getLoggedInUser() {

    return JSON.parse(
      localStorage.getItem(
        'loggedInUser'
      ) || 'null'
    );
  }

  isLoggedIn(): boolean {

    return !!this.getLoggedInUser();
  }
}