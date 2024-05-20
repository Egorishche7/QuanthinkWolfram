import { HttpClient } from 'react-native-http-client';
import { Observable } from 'rxjs';

export interface UserLogin {
  email: string;
  password: string;
}

export interface User {
  id: string;
  username: string;
  email: string;
  password: string;
}

export class AuthService {
  private baseUrl = 'http://localhost:8080';
  private http = new HttpClient(this.baseUrl);

  registerUser(userDetails: User): Observable<any> {
    return this.http.post('/register', userDetails);
  }

  login(authRequest: UserLogin): Observable<any> {
    return this.http.post<User>('/login', authRequest);
  }

  logout(): Observable<any> {
    return this.http.post('/logout', null);
  }
}