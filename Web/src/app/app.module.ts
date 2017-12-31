import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {LoginComponent} from './account/login.component';
import {RegisterComponent} from './account/register.component';
import {DeviceListComponent} from './gpsTracker/deviceList.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DeviceListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(
      [
        {path: '', component: AppComponent},
        {path: 'devices', component: DeviceListComponent}
      ]
    ),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
