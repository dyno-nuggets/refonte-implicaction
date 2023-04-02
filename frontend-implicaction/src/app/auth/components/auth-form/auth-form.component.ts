import {Component, Input} from '@angular/core';
import {Alert} from "../../../shared/models/alert";

@Component({
  selector: 'app-auth-form',
  templateUrl: './auth-form.component.html',
  styleUrls: ['./auth-form.component.scss']
})
export class AuthFormComponent {

  @Input()
  imageSrc!: string;

  @Input()
  title!: string;

  @Input()
  alert?: Alert;
}
