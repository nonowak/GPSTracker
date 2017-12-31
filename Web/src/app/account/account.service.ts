import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Cookie} from 'ng2-cookies';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {RegisterDTO} from './registerData';

@Injectable()
export class AccountService {

  constructor(private _router: Router, private _http: Http) {
  }

  registerAccount(registerData: RegisterDTO, confirmPassword: string) {
    const headers = new Headers({'Content-type': 'application/json'});
    console.log(JSON.stringify(registerData));
    this._http.post('http://localhost:8080/users/register',
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
    this._http.post('http://localhost:8080/oauth/token', params.toString(), options)
      .map(res => res.json())
      .subscribe(
        data => this.saveToken(data),
        err => alert('Invalid Credentials')
      );
  }

  saveToken(token) {
    const expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set('access_token', token.access_token, expireDate);
    console.log('Obtained Access Token' + token.access_token);
    this._router.navigate(['/devices']);
  }

  checkCredentials() {
    if (!Cookie.check('access_token')) {
      this._router.navigate(['/login']);
    }
  }

  logout() {
    Cookie.delete('access_token');
    this._router.navigate(['/login']);
  }

  private handleError(error: any): Promise<any> {
    console.error('Error', error);
    return Promise.reject(error.message || error);
  }
}
