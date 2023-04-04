import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-profile-about-section',
  templateUrl: './profile-about-section.component.html',
})
export class ProfileAboutSectionComponent {

  @Input() content: string;
}
