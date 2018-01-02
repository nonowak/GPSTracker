import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {LoginComponent} from './account/login.component';
import {RegisterComponent} from './account/register.component';
import {DeviceListComponent} from './gpsTracker/deviceList.component';
import {MainPageComponent} from './account/mainPage.component';
import {NavbarComponent} from './navbar/navbar.component';
import {MapComponent} from "./map/map.component";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DeviceListComponent,
    MainPageComponent,
    NavbarComponent,
    MapComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(
      [
        {path: '', component: MainPageComponent},
        {path: 'devices', component: DeviceListComponent},
        {path: 'map/:deviceId', component: MapComponent}
      ]
    ),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
