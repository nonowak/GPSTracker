import {Injectable} from '@angular/core';

import {Headers, Http} from '@angular/http';
import {Cookie} from 'ng2-cookies';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {Subject} from 'rxjs/Subject';
import {DeviceDetailsDTO} from './DTO/DeviceDetailsDTO';
import {DeviceNameDTO} from './DTO/DeviceNameDTO';
import {Observable} from 'rxjs/Observable';
import {UserDeviceDTO} from './DTO/UserDeviceDTO';

@Injectable()
export class DeviceDetailsService {

  private deviceId = new Subject<number>();
  private deviceDetails = new Subject<DeviceDetailsDTO>();

  deviceIdObs = this.deviceId.asObservable();
  deviceDetailsObs = this.deviceDetails.asObservable();

  constructor(private _http: Http) {
  }

  getUserDevices(deviceId) {
    this.deviceId.next(deviceId);
    const headers = new Headers({'Authorization': 'Bearer ' + Cookie.get('access_token')});
    this._http.get('http://localhost:8080/devices/' + deviceId.toString(), {headers: headers})
      .toPromise()
      .then(response => {
        const deviceDetails = response.json() as DeviceDetailsDTO;
        this.deviceDetails.next(deviceDetails);
      });
  }

  updateDeviceName(deviceId, deviceNameDTO: DeviceNameDTO) {
    const headers = new Headers({
      'Authorization': 'Bearer ' + Cookie.get('access_token'), 'Content-type': 'application/json'
    });

    this._http.put('http://localhost:8080/devices/' + deviceId.toString(), JSON.stringify(deviceNameDTO), {headers: headers})
      .toPromise()
      .then(response => {
        const deviceDetails = response.json() as DeviceDetailsDTO;
        this.deviceDetails.next(deviceDetails);
      });
  }

  addUserDevice(deviceId, userDeviceDTO: UserDeviceDTO): Observable<UserDeviceDTO[]> {
    const headers = new Headers({
      'Authorization': 'Bearer ' + Cookie.get('access_token'), 'Content-type': 'application/json'
    });
    return this._http.post('http://localhost:8080/devices/' + deviceId.toString() + '/users',
      JSON.stringify(userDeviceDTO), {headers: headers})
      .map((res: any) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }
}
