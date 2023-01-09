import {Component, Input} from '@angular/core';
import {SidebarService} from "../../../shared/services/sidebar.service";
import {CreateTopicFormComponent} from "../create-topic-form/create-topic-form.component";

@Component({
  selector: 'app-create-topic-button',
  templateUrl: './create-topic-button.component.html',
  styleUrls: ['./create-topic-button.component.scss']
})
export class CreateTopicButtonComponent {

  @Input() buttonName: string = '';

  constructor(private sidebarService: SidebarService) {
  }

  onClick() {
    this.sidebarService.open({
        title: "Créer un topic",
        component: CreateTopicFormComponent,
        width: 500
      }
    );

  }

}
