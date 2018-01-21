import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DeviceDetailsService} from './deviceDetails.service';
import {DeviceDetailsDTO} from './DTO/DeviceDetailsDTO';
import {DeviceNameDTO} from './DTO/DeviceNameDTO';
import {UserDeviceDTO} from './DTO/UserDeviceDTO';

@Component({
  selector: 'app-devicedetails',
  templateUrl: './deviceDetails.component.html',
  providers: [DeviceDetailsService]
})
export class DeviceDetailsComponent implements OnInit {

  private deviceId: number;
  @Input() deviceDetails: DeviceDetailsDTO = new DeviceDetailsDTO();
  deviceNameDTO: DeviceNameDTO = new DeviceNameDTO();
  newUserDeviceDTO: UserDeviceDTO = new UserDeviceDTO();

  constructor(private _service: DeviceDetailsService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route
      .params
      .subscribe(params => {
        this.deviceId = +params['deviceId'] || 0;
      });
    this._service.getUserDevices(this.deviceId);
    this.getDeviceDetails();
  }

  getDeviceDetails() {
    this._service.deviceDetailsObs.subscribe(
      data => {
        this.deviceDetails = data;
        this.deviceNameDTO.name = this.deviceDetails.name;
      },
      error => console.log(error)
    );
  }

  update() {
    this._service.updateDeviceName(this.deviceId, this.deviceNameDTO);
  }

  addUserDevice() {
    this._service.addUserDevice(this.deviceId, this.newUserDeviceDTO).subscribe(
      data => {
        this.newUserDeviceDTO = new UserDeviceDTO();
        this.deviceDetails.users = data;
      },
      error => console.log(error)
    );
  }
}
