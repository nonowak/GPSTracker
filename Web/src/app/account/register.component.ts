import {Component} from '@angular/core';
import {AccountService} from './account.service';
import {RegisterDTO} from './registerData';

@Component({
  selector: 'app-register',
  template: `
      <form>
        <div class="form-group">
          <input type="email" class="form-control" [(ngModel)]="registerDTO.emailAddress" name="ngModel"
                 placeholder="Email">
        </div>
        <div class="form-group">
          <div class="form-inline">
            <input type="password" class="form-control col-sm-6" [(ngModel)]="registerDTO.password" name="ngModel"
                   placeholder="Password">
            <input type="password" class="form-control col-sm-6" [(ngModel)]="confirmPassword" name="ngModel"
                   placeholder="Confirm">
          </div>
        </div>
        <div class="form-group">
          <div class="form-inline">
            <input type="text" class="form-control col-sm-6" [(ngModel)]="registerDTO.firstName" name="ngModel"
                   placeholder="First Name">
            <input type="text" class="form-control col-sm-6" [(ngModel)]="registerDTO.lastName" name="ngModel"
                   placeholder="Last Name">
          </div>
        </div>
        <div class="form-group">
          <div class="form-inline">
            <input type="text" class="form-control col-sm-6" [(ngModel)]="registerDTO.countryName" name="ngModel"
                   placeholder="Country">
            <input type="text" class="form-control col-sm-6" [(ngModel)]="registerDTO.cityName" name="ngModel"
                   placeholder="City">
          </div>
        </div>
        <div class="form-group">
          <div class="form-inline">
            <input type="text" class="form-control col-sm-6" [(ngModel)]="registerDTO.streetName" name="ngModel"
                   placeholder="Street">
            <input type="text" class="form-control col-sm-6" [(ngModel)]="registerDTO.postalCode" name="ngModel"
                   placeholder="Postal Code">
          </div>
        </div>
        <div class="form-group">
          <button type="submit" class="btn btn-success" (click)="registerAccount()">Sign up</button>
        </div>
      </form>
  `
})
export class RegisterComponent {
  public confirmPassword: string;
  public registerDTO: RegisterDTO = new RegisterDTO();

  constructor(private _service: AccountService) {
  }

  registerAccount() {
    if (this._service.registerAccount(this.registerDTO, this.confirmPassword)) {
      this.registerDTO = new RegisterDTO;
      this.confirmPassword = '';
    }
  }
}
