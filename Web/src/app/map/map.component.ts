import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MapService} from './map.service';
import {MeasurementDTO} from './measurement';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  providers: [MapService]
})
export class MapComponent implements OnInit {

  private deviceId: number;
  measurements: MeasurementDTO;

  constructor(private _service: MapService,
              private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.route
      .params
      .subscribe(params => {
        this.deviceId = +params['deviceId'] || 0;
      });
    this.getMeasurements(this.deviceId);
  }

  getMeasurements(deviceId: number) {
    this._service.getMeasurements(deviceId)
      .subscribe(
        data => {
          this.measurements = data;
          console.log(this.measurements);
        },
        error => console.log(error)
      );
  }
}
