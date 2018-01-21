import {Component, OnDestroy} from '@angular/core';
import {DeviceService} from './device.service';
import {DeviceDTO} from './DTO/deviceDTO';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-devicelist',
  templateUrl: './deviceList.component.html',
  providers: [DeviceService]
})
export class DeviceListComponent implements OnDestroy {

  devices: DeviceDTO[];
  deviceDTO: DeviceDTO = new DeviceDTO();
  subscription: Subscription;

  constructor(private _service: DeviceService) {
    this._service.getDevices();
    this.subscription = this._service.deviceObs.subscribe(
      device => {
        this.devices = device;
      }
    );
  }

  addDevice() {
    console.log(this.deviceDTO);
    if (this.deviceDTO.token != null &&
      this.deviceDTO.name != null) {
      this._service.addDevice(this.deviceDTO);
      this.deviceDTO = new DeviceDTO();
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  mapRedirect(deviceId: number) {
    this._service.mapRedirect(deviceId);
  }
  deviceDetailsRedirect(deviceId: number) {
    this._service.deviceDetailsRedirect(deviceId);
  }
}
