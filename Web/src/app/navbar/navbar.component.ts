import {Component} from '@angular/core';
import {AccountService} from '../account/account.service';
import {JwtHelper} from 'angular2-jwt';
import {Cookie} from 'ng2-cookies';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  providers: [AccountService]
})
export class NavbarComponent {
  jwtHelper: JwtHelper = new JwtHelper();
  decodedToken: string;

  constructor(private _router: Router, private _service: AccountService) {
  }

  isTokenExpired() {
    if (Cookie.get('access_token') === '' || this.jwtHelper.isTokenExpired(Cookie.get('access_token'))) {
      // console.log(window.location.pathname);
      // if (window.location.pathname !== '/' && window.location.pathname !== '') {
      //   this._router.navigate(['/']);
      // }
      return true;
    } else {
      this.decodedToken = this.decodeToken();
      return false;
    }
  }

  decodeToken() {
    return this.jwtHelper.decodeToken(Cookie.get('access_token'));
  }

  logout() {
    return this._service.logout();
  }
}
