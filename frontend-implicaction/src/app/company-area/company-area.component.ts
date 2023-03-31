import {Component} from '@angular/core';
import {CommonModule} from "@angular/common";
import {SharedModule} from "../shared/shared.module";

@Component({
  standalone: true,
  imports: [
    CommonModule,
    SharedModule
  ],
  templateUrl: './company-area.component.html',
  styleUrls: ['./company-area.component.scss']
})
export class CompanyAreaComponent {

}
