import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {HttpModule} from '@angular/http';


import {AppComponent} from './app.component';
import {LoginComponent} from './account/login.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(
      [
        {path: '', component: AppComponent}
      ]
    )
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
