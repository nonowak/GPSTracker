import {Component} from '@angular/core';
import {AccountService} from './account.service';
import {RegisterDTO} from './registerData';

@Component({
  selector: 'app-register',
  template: `
    <form data-toggle="validator" role="form">
      <div class="form-group">
        <input type="email" class="form-control" [(ngModel)]="registerDTO.emailAddress" placeholder="Email"
               data-error="Invalid email address" #registerDTO.emailAddress required name="emailAddress">
        <div class="help-block with-errors"></div>
      </div>
      <div class="form-group">
        <div class="form-inline row">
          <div class="form-group">
            <input type="password" class="form-control" [(ngModel)]="registerDTO.password" id="password"
                   placeholder="Password"
                   data-minlength="8" data-error="Invalid password" required name="password">
            <div class="help-block with-errors"></div>
          </div>
          <div class="form-group">
            <input type="password" class="form-control" id="inputPasswordConfirm"
                   data-match="#password"
                   data-match-error="Whoops, these don't match" placeholder="Confirm" required>
            <div class="help-block with-errors"></div>
          </div>
        </div>
      </div>
      <div class="form-group">
        <button type="submit" (click)="registerAccount()" class="btn btn-success">Sign up</button>
      </div>
    </form>
  `
})
export class RegisterComponent {
  public registerDTO: RegisterDTO = new RegisterDTO;

  constructor(private _service: AccountService) {
  }

  registerAccount() {
    this._service.registerAccount(this.registerDTO);
  }
}
