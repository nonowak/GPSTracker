import {Injectable} from '@angular/core';

import {Headers, Http} from '@angular/http';
import {Cookie} from 'ng2-cookies';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {DeviceDTO} from './DTO/deviceDTO';
import {Router} from '@angular/router';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class DeviceService {
  private devices = new Subject<DeviceDTO[]>();

  deviceObs = this.devices.asObservable();

  constructor(private _router: Router, private _http: Http) {
  }

  getDevices() {
    const headers = new Headers({'Authorization': 'Bearer ' + Cookie.get('access_token')});
    return this._http.get('http://localhost:8080/devices', {headers: headers})
      .toPromise()
      .then(
        response => {
          const devices = response.json() as DeviceDTO[];
          this.devices.next(devices);
        })
      .catch((error: any) => (alert(error.status)));
  }

  mapRedirect(deviceId: number) {
    this._router.navigate(['/map', deviceId]);
  }

  addDevice(deviceDTO: DeviceDTO) {
    const headers = new Headers({
      'Authorization': 'Bearer ' + Cookie.get('access_token'),
      'Content-type': 'application/json'
    });
    return this._http.post('http://localhost:8080/devices', JSON.stringify(deviceDTO),
      {headers: headers})
      .toPromise()
      .then(
        response => {
          const devices = response.json() as DeviceDTO[];
          alert('Device added ' + deviceDTO.token);
          this.devices.next(devices);
        }
      ).catch((error: any) => {
        console.log(error);
        alert(error.status);
      });
  }
}
