import {Component} from '@angular/core';
import {AccountService} from './account.service';

@Component({
  selector: 'app-login',
  template: `
    <form class="form-inline" role="form">
      <input class="form-control col-sm-5" type="email" placeholder="Email" [(ngModel)]="loginData.username"
             name="ngModel"/>
      <input class="form-control col-sm-5" type="password" placeholder="Password" [(ngModel)]="loginData.password"
             name="ngModel"/>
      <button type="submit" class="btn btn-primary col-sm-2" (click)="obtainAccessToken()">Sign In</button>
    </form>
  `,
})
export class LoginComponent {
  public loginData = {username: '', password: ''};

  constructor(private _service: AccountService) {
  }

  login() {
    this._service.obtainAccessToken(this.loginData);
  }
}
