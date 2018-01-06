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
import {AuthenticatedHttpService} from './account/authenticated.service';
import {AgmCoreModule, LazyMapsAPILoaderConfigLiteral} from '@agm/core';
import {AgmJsMarkerClustererModule} from '@agm/js-marker-clusterer';
import {DatepickerComponent} from './map/datepicker.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AdminComponent} from './admin/admin.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DeviceListComponent,
    MainPageComponent,
    NavbarComponent,
    MapComponent,
    UserInfoComponent,
    DatepickerComponent,
    AdminComponent,
    // AddDeviceComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    NgbModule.forRoot(),
    RouterModule.forRoot(
      [
        {path: '', component: MainPageComponent},
        {path: 'devices', component: DeviceListComponent},
        {path: 'map/:deviceId', component: MapComponent},
        {path: 'userInfo', component: UserInfoComponent},
        {path: 'admins', component: AdminComponent},
      ]
    ),
    AgmCoreModule.forRoot(<LazyMapsAPILoaderConfigLiteral>{
      apiKey: 'AIzaSyAPHQ9DoyyOlULYeb4p49cvoVTCOH2f1Og'
    }),
    AgmJsMarkerClustererModule
  ],
  providers: [
    {provide: Http, useClass: AuthenticatedHttpService}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
