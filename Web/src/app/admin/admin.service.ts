import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {DeviceDictionaryDTO} from './DTO/DeviceDictionaryDTO';
import {Cookie} from 'ng2-cookies';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';
import {Http, Headers} from '@angular/http';

@Injectable()
export class AdminService {

  constructor(private _router: Router, private _http: Http) {
  }

  addDevice(deviceDTO: DeviceDictionaryDTO): Observable<DeviceDictionaryDTO[]> {
    console.log(deviceDTO);
    const headers = new Headers({
      'Authorization': 'Bearer ' + Cookie.get('access_token'),
      'Content-type': 'application/json'
    });
    return this._http.post('http://localhost:8080/admins/devices', JSON.stringify(deviceDTO),
      {headers: headers})
      .map((res: any) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }

  getDevices(): Observable<DeviceDictionaryDTO[]> {
    const headers = new Headers({'Authorization': 'Bearer ' + Cookie.get('access_token')});
    return this._http.get('http://localhost:8080/admins/devices', {headers: headers})
      .map((res: any) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }
}

