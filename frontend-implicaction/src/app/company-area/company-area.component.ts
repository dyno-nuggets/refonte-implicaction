import {Component} from '@angular/core';
import {SharedModule} from "../shared/shared.module";

@Component({
  standalone: true,
  imports: [SharedModule],
  templateUrl: './company-area.component.html',
  styleUrls: ['./company-area.component.scss']
})
export class CompanyAreaComponent {

}
