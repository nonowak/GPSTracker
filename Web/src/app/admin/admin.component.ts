import {AdminService} from './admin.service';
import {Component, OnInit} from '@angular/core';
import {DeviceDictionaryDTO} from './DTO/DeviceDictionaryDTO';

@Component({
  selector: 'app-admin',
  template: `
    <div style="margin-top: 10px">
      <div class="row">
        <div class="col-md-10"><h4>Devices</h4></div>
        <div class="col-md-2">
          <h4>
            <button class="btn-success" (click)=addDevice()>Add Device</button>
          </h4>
        </div>
      </div>
      <table class="table">
        <thead>
        <tr>
          <th>Type</th>
          <th>Created On</th>
          <th>Created By</th>
          <th>Token</th>
          <th>Enabled</th>
        </tr>
        </thead>
        <tbody>
        <tr *ngFor="let device of devices">
          <td>{{device.deviceType}}</td>
          <td>{{device.createdOn}}</td>
          <td>{{device.createdByEmailAddress}}</td>
          <td>{{device.token}}</td>
          <td>{{device.enabled}}</td>
        </tr>
        </tbody>
      </table>
    </div>`
  ,
  providers: [AdminService]
})
export class AdminComponent implements OnInit {
  devices: DeviceDictionaryDTO[];

  constructor(private service: AdminService) {
  }

  ngOnInit(): void {
    this.getDevices();
  }

  addDevice() {
    const deviceDTO = new DeviceDictionaryDTO();
    console.log('dupa' + deviceDTO.deviceType);
    this.service.addDevice(deviceDTO).subscribe(
      data => this.devices = data,
      error => console.log(error)
    );
  }

  getDevices() {
    this.service.getDevices().subscribe(
      data => this.devices = data,
      error => console.log(error)
    );
  }
}
