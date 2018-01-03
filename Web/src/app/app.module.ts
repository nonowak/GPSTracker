import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {Http, HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {LoginComponent} from './account/login.component';
import {RegisterComponent} from './account/register.component';
import {DeviceListComponent} from './gpsTracker/deviceList.component';
import {MainPageComponent} from './account/mainPage.component';
import {NavbarComponent} from './navbar/navbar.component';
import {MapComponent} from './map/map.component';
import {UserInfoComponent} from './account/userInfo.component';
import {AuthenticatedHttpService} from "./account/authenticated.service";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DeviceListComponent,
    MainPageComponent,
    NavbarComponent,
    MapComponent,
    UserInfoComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(
      [
        {path: '', component: MainPageComponent},
        {path: 'devices', component: DeviceListComponent},
        {path: 'map/:deviceId', component: MapComponent},
        {path: 'userInfo', component: UserInfoComponent}
      ]
    ),
  ],
  providers: [
    {provide: Http, useClass: AuthenticatedHttpService}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
