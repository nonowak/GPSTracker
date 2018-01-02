import {Component} from '@angular/core';
import {AccountService} from '../account/account.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  providers: [AccountService]
})
export class NavbarComponent {
  constructor(private _service: AccountService) {
  }
}
