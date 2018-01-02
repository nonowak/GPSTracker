import {Component, OnInit} from '@angular/core';
import {DeviceService} from './device.service';
import {DeviceDTO} from './deviceDTO';

@Component({
  selector: 'app-devicelist',
  templateUrl: './deviceList.component.html',
  providers: [DeviceService]
})
export class DeviceListComponent implements OnInit {
  devices: DeviceDTO[];

  constructor(private _service: DeviceService) {
  }

  ngOnInit() {
    this.getDevices();
  }

  getDevices() {
    this._service.getDevices()
      .subscribe(
        data => this.devices = data,
        error => console.log(error)
      )
    ;
  }
}
