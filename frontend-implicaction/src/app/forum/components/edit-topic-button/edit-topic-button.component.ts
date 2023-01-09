import {Component, Input} from '@angular/core';
import {SidebarService} from "../../../shared/services/sidebar.service";
import {EditTopicFormComponent} from "../edit-topic-form/edit-topic-form.component";
import {Topic} from '../../model/topic';

@Component({
  selector: 'app-edit-topic-button',
  templateUrl: './edit-topic-button.component.html',
  styleUrls: ['./edit-topic-button.component.scss']
})
export class EditTopicButtonComponent {

  @Input() buttonName: string = '';
  @Input() topicId?: number;
  @Input() topic?: Topic;


  constructor(private sidebarService: SidebarService) {
  }

  onClick() {
    this.sidebarService.open({
      title: 'Editer un topic',
      component: EditTopicFormComponent,
      width: 500,
      input: this.topic ?? this.topicId
    });
  }

}
