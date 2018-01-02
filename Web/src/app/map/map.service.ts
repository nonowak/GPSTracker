import {Injectable} from '@angular/core';

import {Headers, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Cookie} from 'ng2-cookies';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {MeasurementDTO} from './measurement';

@Injectable()
export class MapService {
  constructor(private _http: Http) {

  }

  getMeasurements(deviceId: number): Observable<MeasurementDTO> {
    const headers = new Headers({'Authorization': 'Bearer ' + Cookie.get('access_token')});
    return this._http.get('http://localhost:8080/gps/measurements/' + deviceId.toString(), {headers: headers})
      .map((res: any) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }

}
