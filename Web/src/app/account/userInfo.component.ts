import {Component, Input, OnInit} from '@angular/core';
import {AccountService} from './account.service';
import {UserDTO} from './DTO/updateUser';

@Component({
  selector: 'app-userinfo',
  template: `
    <form novalidate id="updateUser" role="form">
      <div class="input-group col-md-2">
        <label>First Name</label>
        <input class="form-control" type="text" [(ngModel)]="userDTO.firstName"
               name="userDTO.firstName"/>
      </div>

      <div class="input-group col-md-2">
        <label>Last Name</label>
        <input class="form-control" type="text" [(ngModel)]="userDTO.lastName"
               name="userDTO.lastName"/>
      </div>

      <div class="input-group col-md-2">
        <label>Country Name</label>
        <input class="form-control" type="text" [(ngModel)]="userDTO.countryName"
               name="userDTO.countryName"/>
      </div>

      <div class="input-group col-md-2">
        <label>City Name</label>
        <input class="form-control" type="text" [(ngModel)]="userDTO.cityName"
               name="userDTO.cityName"/>
      </div>

      <div class="input-group col-md-2">
        <label>Street Name</label>
        <input class="form-control" type="text" [(ngModel)]="userDTO.streetName"
               name="userDTO.streetName"/>
      </div>

      <div class="input-group col-md-2">
        <label>Street Name</label>
        <input class="form-control" type="text" [(ngModel)]="userDTO.postalCode"
               name="userDTO.postalCode"/>
      </div>
      <div class="input-group col-md-2">
        <button type="submit" class="btn btn-warning" (click)="update()">Update</button>
      </div>
    </form>
  `,
  providers: [AccountService]
})
export class UserInfoComponent implements OnInit {
  @Input() private userDTO: UserDTO = new UserDTO()

  constructor(private _service: AccountService) {
  }

  ngOnInit() {
    this._service.getUserInfo()
      .subscribe(
        data => {
          this.userDTO = data;
          console.log(this.userDTO);
        },
        error => console.log(error)
      );
  }

  getUserInfo() {
    this._service.getUserInfo()
      .subscribe(
        data => {
          this.userDTO = data;
          console.log(this.userDTO);
        },
        error => console.log(error)
      );
  }

  update() {
    this._service.updateUser(this.userDTO)
      .subscribe(
        data => {
          this.userDTO = data;
          console.log(this.userDTO);
          this.getUserInfo();
        },
        error => console.log(error)
      );
  }


}
