export class DeviceDictionaryDTO {
  deviceType: string;
  createdOn: Date;
  createdByEmailAddress: string;
  token: string;
  enabled: boolean;

  constructor() {
    this.deviceType = 'GPSTRACKER';
  }
}
