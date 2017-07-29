import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  username = '';

  onCreateServer() {
    console.log('ciao, ' + this.username);
  }

  isAddDisabled() {
    return this.username === '';
  }
}
