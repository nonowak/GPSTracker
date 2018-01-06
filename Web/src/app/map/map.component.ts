import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MapService} from './map.service';
import {MeasurementDTO} from './measurement';
import {AgmMap, GoogleMapsAPIWrapper} from '@agm/core';

declare let google: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  providers: [MapService, GoogleMapsAPIWrapper]
})
export class MapComponent implements OnInit {

  private deviceId: number;
  @Input() measurements: MeasurementDTO[];
  map: any;

  constructor(private _service: MapService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route
      .params
      .subscribe(params => {
        this.deviceId = +params['deviceId'] || 0;
      });
    this.getMeasurements();
  }

  setMap(map) {
    this.map = map;
  }

  getMeasurements() {
    this._service.getMeasurements(this.deviceId)
    this._service.measurementsObs.subscribe(
      data => {
        console.log(data);
        this.measurements = data;
        console.log(this.measurements);
      },
      error => console.log(error)
    );
  }

  show(measurement: MeasurementDTO) {
    console.log(this.map);
    this.map.panTo(new google.maps.LatLng(measurement.latLng.lat, measurement.latLng.lng));
    this.map.setZoom(20);
  }
}
