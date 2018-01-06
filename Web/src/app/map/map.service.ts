import {Injectable} from '@angular/core';

import {Headers, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Cookie} from 'ng2-cookies';

import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {MeasurementDTO} from './measurement';
import {FirstAndLastMeasurementDateDTO} from './DTO/FirstAndLastMeasurementDateDTO';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class MapService {

  private deviceId = new Subject<number>();
  private measurements = new Subject<MeasurementDTO[]>();

  deviceIdObs = this.deviceId.asObservable();
  measurementsObs = this.measurements.asObservable();

  constructor(private _http: Http) {
  }

  getMeasurements(deviceId) {
    this.deviceId.next(deviceId);
    const headers = new Headers({'Authorization': 'Bearer ' + Cookie.get('access_token')});
    this._http.get('http://localhost:8080/gps/measurements/' + deviceId.toString(), {headers: headers})
      .toPromise()
      .then(response => {
        const measurementsa = response.json() as MeasurementDTO[];
        this.measurements.next(measurementsa);
      });
  }

  getFirstAndLastMeasurement(deviceId): Observable<FirstAndLastMeasurementDateDTO> {
    const headers = new Headers({'Authorization': 'Bearer ' + Cookie.get('access_token')});
    return this._http.get('http://localhost:8080/gps/measurements/'
      + deviceId.toString() + '/dates', {headers: headers})
      .map((res: any) => res.json())
      .catch((error: any) => Observable.throw(error.json()));
  }

  getMeasuramentsBetween(deviceId, fromDate: Date, toDate: Date) {
    const headers = new Headers({'Authorization': 'Bearer ' + Cookie.get('access_token')});

    return this._http.get('http://localhost:8080/gps/measurements/'
      + deviceId.toString() + '?startDate=' + this.toISOdate(fromDate) + '&endDate=' + this.toISOdate(toDate),
      {headers: headers})
      .toPromise()
      .then(response => {
        const measurementsa = response.json() as MeasurementDTO[];
        this.measurements.next(measurementsa);
      });
  }

  toISOdate(date: Date): string {
    const year = date.getFullYear();
    let month: string = (date.getMonth() + 1).toString();
    let dt: string = date.getDate().toString();

    if (date.getDate() < 10) {
      dt = '0' + dt;
    }
    if (date.getMonth() + 1 < 10) {
      month = '0' + month;
    }
    return year + '-' + month + '-' + dt;
  }
}
