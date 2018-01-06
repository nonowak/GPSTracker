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
  constructor(private _service: AccountService) {
  }

  logout() {
    return this._service.logout();
  }
}
