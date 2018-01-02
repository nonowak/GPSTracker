export class Address {
  countryName: string;
  cityName: string;
  streetName: string;
}

export class LatLng {
  lat: number;
  lng: number;
}

export class MeasurementDTO {
  address: Address;
  latLng: LatLng;
  measurementTime: string;
  measurementDate: string;
}
