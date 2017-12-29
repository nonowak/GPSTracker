import {Component} from '@angular/core';
import {AccountService} from './account/account.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers: [AccountService]
})
export class AppComponent {
  title = 'app';

  constructor(private _service: AccountService) {
  }
}
