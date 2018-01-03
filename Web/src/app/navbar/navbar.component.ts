import {Component} from '@angular/core';
import {AccountService} from '../account/account.service';
import {JwtHelper} from 'angular2-jwt';
import {Cookie} from 'ng2-cookies';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  providers: [AccountService]
})
export class NavbarComponent {
  jwtHelper: JwtHelper = new JwtHelper();
  decodedToken: string;

  constructor(private _service: AccountService) {
  }

  decodeToken() {
    return this.jwtHelper.decodeToken(Cookie.get('access_token'));
  }

  logout() {
    return this._service.logout();
  }
}
