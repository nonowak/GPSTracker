export class DeviceDTO {
  id: number;
  deviceType = 'GPSTRACKER';
  name: string;
  lastMeasurementDate: string;
  permission: string;
  token: string;

  constructor(instanceData?: DeviceDTO) {
    if (instanceData) {
      this.deserialize(instanceData);
    }
  }

  private deserialize(instanceData: DeviceDTO) {
    // Note this.active will not be listed in keys since it's declared, but not defined
    const keys = Object.keys(this);

    for (const key of keys) {
      if (instanceData.hasOwnProperty(key)) {
        this[key] = instanceData[key];
      }
    }
  }
}
