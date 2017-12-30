import {Component} from '@angular/core';
import {AccountService} from './account.service';

@Component({
  selector: 'app-login',
  template: `
    <form class="form-inline" data-toggle="validator" role="form" id="loginForm">
      <input class="form-control col-lg-5" type="email" placeholder="Email" [(ngModel)]="loginData.username"
             required name="username"/>
      <div class="help-block with-errors"></div>
      <input class="form-control col-lg-5" type="password" placeholder="Password" [(ngModel)]="loginData.password"
             data-minlength="8"
             data-error="Invalid password"
             #loginData.password required name="password"/>
      <div class="help-block with-errors"></div>
      <div class="form-group col-lg-2">
        <button type="submit" class="btn btn-primary" id="signIn" (click)="login()">Sign In
        </button>
      </div>
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
