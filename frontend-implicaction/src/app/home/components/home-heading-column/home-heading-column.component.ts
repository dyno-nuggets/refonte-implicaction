import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-home-heading-column',
  templateUrl: './home-heading-column.component.html',
})
export class HomeHeadingColumnComponent {

  @Input()
  title: string;
  @Input()
  buttonLink: string;

}
