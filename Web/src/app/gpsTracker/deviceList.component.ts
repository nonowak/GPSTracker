import {Component} from '@angular/core';
import {DeviceService} from './device.service';

@Component({
  selector: 'app-devicelist',
  templateUrl: './deviceList.component.html',
  providers: [DeviceService]
})
export class DeviceListComponent {
  constructor(private _service: DeviceService) {
  }

  getDevices() {
    this._service.getDevices()
      .subscribe(
        data => console.log(JSON.stringify(data)),
        error => console.log('Server Error')
      );
  }
}
