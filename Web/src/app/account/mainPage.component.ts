import {Component} from '@angular/core';
import {AccountService} from './account.service';

@Component({
  selector: 'app-mainpage',
  templateUrl: './mainPage.component.html',
  providers: [AccountService]
})
export class MainPageComponent {

  constructor(private _service: AccountService) {
  }
}
