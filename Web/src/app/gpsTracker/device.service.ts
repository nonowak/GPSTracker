import {Injectable} from '@angular/core';

import {Headers, Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Cookie} from 'ng2-cookies';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class DeviceService {
  constructor(private _http: Http) {

  }

  getDevices(): Observable<any> {
    const headers = new Headers({'Authorization': 'Bearer ' + Cookie.get('access_token')});
    return this._http.get('http://localhost:3000/stocks', {headers: headers})
      .map((response: Response) => response.json())
      .catch((error: any) => Observable.throw(error.json()));
  }

}
