import {Component} from '@angular/core';
import {AccountService} from './account.service';

@Component({
  selector: 'app-login',
  template: `
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <form id="signin" class="navbar-form navbar-right" role="form">
        <div class="input-group">
          <input class="form-control" type="email" placeholder="Email" [(ngModel)]="loginData.username" name="ngModel"/>
        </div>

        <div class="input-group">
          <input class="form-control" type="password" placeholder="Password" [(ngModel)]="loginData.password"
                 name="ngModel"/>
        </div>

        <button type="submit" class="btn btn-primary" (click)="login()">Sign In</button>
      </form>
    </div>
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
