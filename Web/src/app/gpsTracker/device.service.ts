import {Injectable} from '@angular/core';

import {Headers, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Cookie} from 'ng2-cookies';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {DeviceDTO} from './deviceDTO';

@Injectable()
export class DeviceService {
  constructor(private _http: Http) {

  }

  getDevices(): Observable<DeviceDTO> {
    const headers = new Headers({'Authorization': 'Bearer ' + Cookie.get('access_token')});
    return this._http.get('http://localhost:8080/devices', {headers: headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }

}
