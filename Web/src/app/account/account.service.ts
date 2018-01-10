import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Cookie} from 'ng2-cookies';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {RegisterDTO} from './DTO/registerData';
import {Observable} from 'rxjs/Observable';
import {UserDTO} from './DTO/updateUser';
import {JwtHelper} from 'angular2-jwt';

@Injectable()
export class AccountService {

  jwtHelper: JwtHelper = new JwtHelper();


  constructor(private _router: Router, private _http: Http) {
  }

  registerAccount(registerData: RegisterDTO, confirmPassword: string) {
    const headers = new Headers({'Content-type': 'application/json'});
    console.log(JSON.stringify(registerData));
    this._http.post('http://e6632eca.eu.ngrok.io/users/register',
      JSON.stringify(registerData),
      {headers: headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
    return true;
  }

  obtainAccessToken(loginData) {
    const params = new URLSearchParams();
    params.append('username', loginData.username);
    params.append('password', loginData.password);
    params.append('grant_type', 'password');
    params.append('client_id', 'gpsTracker');

    const headers = new Headers({
      'Content-type': 'application/x-www-form-urlencoded;',
      'Authorization': 'Basic Z3BzVHJhY2tlcjpzZWNyZXQ='
    });
    const options = new RequestOptions({headers: headers});
    console.log(params.toString());
    this._http.post('http://e6632eca.eu.ngrok.io/oauth/token', params.toString(), options)
      .map(res => res.json())
      .subscribe(
        data => this.saveToken(data),
        err => alert('Invalid Credentials')
      );
  }

  saveToken(token) {
    const expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set('access_token', token.access_token, expireDate);
    if (this.decodeToken().authorities[0].authority === 'ROLE_ADMIN') {
      this._router.navigate(['/admins']);
    } else {
      this._router.navigate(['/devices']);
    }
  }

  logout() {
    Cookie.delete('access_token');
    this._router.navigate(['/']);
  }

  private handleError(error: any): Promise<any> {
    console.error('Error', error);
    return Promise.reject(error.message || error);
  }

  decodeToken() {
    return this.jwtHelper.decodeToken(Cookie.get('access_token'));
  }

  getUserInfo(): Observable<UserDTO> {
    const headers = new Headers({'Authorization': 'Bearer ' + Cookie.get('access_token')});
    return this._http.get('http://e6632eca.eu.ngrok.io/users/info', {headers: headers})
      .map((res: any) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }

  updateUser(userDTO: UserDTO): Observable<UserDTO> {
    const headers = new Headers({
      'Authorization': 'Bearer ' + Cookie.get('access_token'), 'Content-type': 'application/json'
    });
    return this._http.put('http://e6632eca.eu.ngrok.io/users/info', JSON.stringify(userDTO), {headers: headers})
      .map((res: any) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }

  isTokenExpired(): boolean {
    if (Cookie.get('access_token')) {
      return this.jwtHelper.isTokenExpired(Cookie.get('access_token'));
    } else {
      return true;
    }
  }
}
