import {Component} from '@angular/core';
import {AccountService} from './account.service';

@Component({
  selector: 'app-login',
  template: `
    <div class="col-md-12 form-group">
      <h1>Login</h1>
      <input class="form-control" type="email" [(ngModel)]="loginData.username" placeholder="Email"/>
      <input class=" form-control" type="password" [(ngModel)]="loginData.password" placeholder="Password"/>
      <button class="btn-primary" (click)="login()" type="submit">Login</button>
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
