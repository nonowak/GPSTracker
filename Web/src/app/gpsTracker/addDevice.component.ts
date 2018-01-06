import {Component, OnInit} from '@angular/core';
import {DeviceService} from './device.service';
import {DeviceDTO} from './DTO/deviceDTO';

@Component({
  selector: 'app-devicelist',
  templateUrl: './deviceList.component.html',
  providers: [DeviceService]
})
export class AddDeviceComponent {
  devices: DeviceDTO;

  constructor(private _service: DeviceService) {
  }

  addDevice(deviceDTO: DeviceDTO) {
    this._service.addDevice(deviceDTO);
  }
}
