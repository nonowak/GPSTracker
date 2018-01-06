import {Component, Input, OnDestroy} from '@angular/core';
import {NgbCalendar, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {MapService} from './map.service';
import {FirstAndLastMeasurementDateDTO} from './DTO/FirstAndLastMeasurementDateDTO';
import {Subscription} from 'rxjs/Subscription';

const equals = (one: NgbDateStruct, two: NgbDateStruct) =>
  one && two && two.year === one.year && two.month === one.month && two.day === one.day;

const before = (one: NgbDateStruct, two: NgbDateStruct) =>
  !one || !two ? false : one.year === two.year ? one.month === two.month ? one.day === two.day
    ? false : one.day < two.day : one.month < two.month : one.year < two.year;

const after = (one: NgbDateStruct, two: NgbDateStruct) =>
  !one || !two ? false : one.year === two.year ? one.month === two.month ? one.day === two.day
    ? false : one.day > two.day : one.month > two.month : one.year > two.year;

@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styles: [`
    .custom-day {
      text-align: center;
      padding: 0.185rem 0.25rem;
      display: inline-block;
      height: 2rem;
      width: 2rem;
    }

    .custom-day.focused {
      background-color: #e6e6e6;
    }

    .custom-day.range, .custom-day:hover {
      background-color: rgb(2, 117, 216);
      color: white;
    }

    .custom-day.faded {
      background-color: rgba(2, 117, 216, 0.5);
    }
  `]
})
export class DatepickerComponent implements OnDestroy {

  @Input() deviceId: number;
  hoveredDate: NgbDateStruct;
  fromDate: NgbDateStruct;
  staticFromDate: NgbDateStruct;
  staticToDate: NgbDateStruct;
  toDate: NgbDateStruct;
  falDate: FirstAndLastMeasurementDateDTO;
  subscription: Subscription;
  button = 'Change Date Range';

  constructor(private mapService: MapService, private calendar: NgbCalendar) {
    this.fromDate = calendar.getToday();
    this.toDate = calendar.getNext(calendar.getToday(), 'd', 10);
    this.subscription = this.mapService.deviceIdObs.subscribe(
      deviceId => {
        this.deviceId = deviceId;
        this.getDates(this.deviceId);
      }
    );
  }

  parseDate(date: NgbDateStruct): Date {
    if (date !== null) {
      return new Date(date.year, date.month, date.day);
    } else {
      return null;
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  getMeasurementsBetween() {
    this.updateButton();
    if (this.toDate == null) {
      this.mapService.getMeasuramentsBetween(this.deviceId, this.parseDate(this.fromDate), this.parseDate(this.fromDate));
    } else {
      this.mapService.getMeasuramentsBetween(this.deviceId, this.parseDate(this.fromDate), this.parseDate(this.toDate));
    }
  }

  getDates(deviceId: number) {
    this.mapService.getFirstAndLastMeasurement(deviceId)
      .subscribe(
        data => {
          this.falDate = data;
          this.falDate.earliestDate = new Date(data.earliestDate);
          this.falDate.lastDate = new Date(data.lastDate);
          this.fromDate = <NgbDateStruct>{
            day: this.falDate.earliestDate.getUTCDate(), month: this.falDate.earliestDate.getUTCMonth(),
            year: this.falDate.earliestDate.getUTCFullYear()
          };
          this.toDate = <NgbDateStruct>{
            day: this.falDate.lastDate.getUTCDate(), month: this.falDate.lastDate.getUTCMonth(),
            year: this.falDate.lastDate.getUTCFullYear()
          };
          this.staticFromDate = this.fromDate;
          this.staticToDate = this.toDate;
        });
  }

  onDateChange(date: NgbDateStruct) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && after(date, this.fromDate)) {
      this.toDate = date;
    } else {
      this.toDate = null;
      this.fromDate = date;
    }
  }

  updateButton() {
    if (this.toDate == null) {
      this.button = this.parseDate(this.fromDate).toLocaleDateString();
    } else {
      this.button = this.parseDate(this.fromDate).toLocaleDateString() + ' - ' + this.parseDate(this.toDate).toLocaleDateString();
    }
  }

  isHovered = date => this.fromDate && !this.toDate && this.hoveredDate && after(date, this.fromDate) && before(date, this.hoveredDate);
  isInside = date => after(date, this.fromDate) && before(date, this.toDate);
  isFrom = date => equals(date, this.fromDate);
  isTo = date => equals(date, this.toDate);
}
