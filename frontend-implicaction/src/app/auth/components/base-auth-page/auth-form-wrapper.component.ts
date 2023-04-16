import {Component, Input} from '@angular/core';
import {Alert} from '../../../shared/models/alert';

@Component({
  selector: 'app-auth-wrapper-page',
  templateUrl: './auth-form-wrapper.component.html',
  styleUrls: ['./auth-form-wrapper.component.scss']
})
export class AuthFormWrapperComponent {

  @Input() imageSrc!: string;
  @Input() title!: string;
  @Input() alert?: Alert;
}
