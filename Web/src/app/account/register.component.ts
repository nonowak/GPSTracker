import {Component} from '@angular/core';
import {AccountService} from './account.service';
import {RegisterDTO} from './DTO/registerData';

@Component({
  selector: 'app-register',
  template: `
    <form>
      <div class="form-group">
        <input type="email" class="form-control" [(ngModel)]="registerDTO.emailAddress" name="ngModel"
               placeholder="Email">
      </div>
      <div class="form-group row">
        <div class="col-md-6">
          <input type="password" class="form-control" [(ngModel)]="registerDTO.password" name="ngModel"
                 placeholder="Password">
        </div>
        <div class="col-md-6">
          <input type="password" class="form-control" [(ngModel)]="confirmPassword" name="ngModel"
                 placeholder="Confirm">
        </div>
      </div>
      <div class="form-group row">
        <div class="col-md-6">
          <input type="text" class="form-control" [(ngModel)]="registerDTO.firstName" name="ngModel"
                 placeholder="First Name">
        </div>
        <div class="col-md-6">
          <input type="text" class="form-control" [(ngModel)]="registerDTO.lastName" name="ngModel"
                 placeholder="Last Name">
        </div>
      </div>
      <div class="form-group row">
        <div class="col-md-6">
          <input type="text" class="form-control" [(ngModel)]="registerDTO.countryName" name="ngModel"
                 placeholder="Country">
        </div>
        <div class="col-md-6">
          <input type="text" class="form-control" [(ngModel)]="registerDTO.cityName" name="ngModel"
                 placeholder="City">
        </div>
      </div>
      <div class="form-group row">
        <div class="col-md-6">
          <input type="text" class="form-control" [(ngModel)]="registerDTO.streetName" name="ngModel"
                 placeholder="Street">
        </div>
        <div class="col-md-6">
          <input type="text" class="form-control" [(ngModel)]="registerDTO.postalCode" name="ngModel"
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
