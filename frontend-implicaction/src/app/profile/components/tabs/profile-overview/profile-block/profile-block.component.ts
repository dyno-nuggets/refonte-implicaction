import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-profile-block',
  templateUrl: './profile-block.component.html',
})
export class ProfileBlockComponent {

  @Input() title: string;

}
