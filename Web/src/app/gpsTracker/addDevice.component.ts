// import {Component, OnInit} from '@angular/core';
// import {DeviceService} from './device.service';
// import {DeviceDTO} from './DTO/deviceDTO';
//
// @Component({
//   selector: 'app-adddevice',
//   template: `
//     <div class="col-md-6">
//       <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
//         Add Device
//       </button>
//     </div>
//
//     <div class="modal fade" id="myModal">
//       <div class="modal-dialog" style="width: 330px">
//         <div class="modal-content">
//
//           <!-- Modal Header -->
//           <div class="modal-header">
//             <h4 class="modal-title">Add Device</h4>
//           </div>
//
//           <!-- Modal body -->
//           <div class="modal-body">
//             <form novalidate id="addDevice" role="form">
//               <label>Token</label>
//               <input class="form-control" type="text" [(ngModel)]="deviceDTO.token" name="token"/>
//               <label>Name</label>
//               <input class="form-control" type="text" [(ngModel)]="deviceDTO.name" name="token"/>
//               <button type="button" class="btn btn-success" data-dismiss="modal" (click)="addDevice()">Add</button>
//             </form>
//           </div>
//
//           <!-- Modal footer -->
//           <div class="modal-footer">
//             <button type="button" class="btn btn-secondary" data-dismiss="modal" (click)="addDevice()">Close</button>
//           </div>
//         </div>
//       </div>
//     </div>
//   `,
//   providers: [DeviceService]
// })
// export class AddDeviceComponent {
//   deviceDTO: DeviceDTO = new DeviceDTO();
//
//   constructor(private _service: DeviceService) {
//   }
//
//   addDevice() {
//     this._service.addDevice(this.deviceDTO);
//   }
// }
